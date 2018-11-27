package com.mall.modules.gift.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.StringUtils;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.gift.dao.GiftConfigCategoryDao;
import com.mall.modules.gift.dao.GiftMerchantDao;
import com.mall.modules.gift.entity.GiftConfigCategory;
import com.mall.modules.gift.entity.GiftCustomer;
import com.mall.modules.gift.entity.GiftMerchant;
import com.mall.modules.gift.entity.GiftTransferLog;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.sys.entity.Role;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 礼包列表Service
 *
 * @author wankang
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class GiftMerchantService extends CrudService<GiftMerchantDao, GiftMerchant> {

    @Autowired
    private GiftTransferLogService giftTransferLogService;
    @Autowired
    private GiftCustomerService giftCustomerService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private GiftConfigCategoryDao giftConfigCategoryDao;
    @Autowired
    private AccountService accountService;

    public GiftMerchant get(String id) {
        return super.get(id);
    }

    public List<GiftMerchant> findList(GiftMerchant giftMerchant) {
        return super.findList(giftMerchant);
    }

    public Page<GiftMerchant> findPage(Page<GiftMerchant> page, GiftMerchant giftMerchant) {
        return super.findPage(page, giftMerchant);
    }

    @Transactional(readOnly = false)
    public void save(GiftMerchant giftMerchant) {
        super.save(giftMerchant);
    }

    @Transactional(readOnly = false)
    public void delete(GiftMerchant giftMerchant) {
        super.delete(giftMerchant);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void giftTransfer(GiftMerchant giftMerchant) throws Exception {
        GiftConfigCategory giftConfigCategory = giftConfigCategoryDao.get(giftMerchant.getGiftConfigCategory());
        GiftTransferLog giftTransferLog = giftTransferLogService.genGiftTransferLog(giftMerchant);
        GiftCustomer giftCustomer = giftCustomerService.genGiftCustomer(giftMerchant);
        String customerMobile = giftMerchant.getCustomerMobile();
        User customer = UserUtils.getByLoginName(customerMobile);
        if (null == customer) {
            throw new ServiceException("用户不存在");
        }

        MemberInfo queryCondition = new MemberInfo();
        queryCondition.setId(customer.getId());
        MemberInfo memberInfo = memberInfoService.get(queryCondition);

        String customerCode = customer.getId();
        giftCustomer.setCustomerCode(customerCode);
        giftTransferLog.setCustomerCode(customerCode);
        Integer stock = giftMerchant.getStock();
        Integer givenCount = giftMerchant.getGivenCount();
        if(stock < 1) {
            throw new ServiceException("可赠送余量不足");
        }
        giftMerchant.setStock(--stock);
        giftMerchant.setGivenCount(++givenCount);
        this.save(giftMerchant);
        giftCustomerService.save(giftCustomer);
        giftTransferLog.setGiftCustomerCode(giftCustomer.getId());
        giftTransferLogService.save(giftTransferLog);
        String merchantQualification = giftConfigCategory.getMerchantQualification();
        String receiverId = "";
        if("1".equals(merchantQualification)) {
            // 如果用户当前不是商户 赋予用户未审核商户权限
            if ("0".equals(customer.getUserType()) && StringUtils.isBlank(memberInfo.getMerchantRefereeId())) {
                List<Role> roleList = customer.getRoleList();
                roleList.add(new Role("1000"));
                customer.setUserType("1");
                systemService.saveUser(customer);
                UserUtils.clearCache();
                String merchantRefereeId = giftCustomer.getTransferMerchantCode();
                MemberInfo memberInfoCondition = new MemberInfo();
                memberInfoCondition.setId(customer.getId());
                memberInfoCondition.setMerchantRefereeId(merchantRefereeId);
                memberInfoService.modifyMerchantRefereeId(memberInfoCondition);
                receiverId = merchantRefereeId;
            }else {
                receiverId = memberInfo.getMerchantRefereeId();
            }
        }
        // 生成礼包佣金
        accountService.createCommissionInfo(giftCustomer.getTransferMerchantCode(), receiverId, giftConfigCategory.getGiftPrice(), giftCustomer.getId(), merchantQualification);
    }

}