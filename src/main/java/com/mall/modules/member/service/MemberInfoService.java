package com.mall.modules.member.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.StringUtils;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.member.dao.MemberFavoriteDao;
import com.mall.modules.member.dao.MemberFootprintDao;
import com.mall.modules.member.dao.MemberInfoDao;
import com.mall.modules.member.dao.MemberRefereeIdMaxDao;
import com.mall.modules.member.entity.*;
import com.mall.modules.sys.entity.Role;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.service.SystemService;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户信息Service
 *
 * @author wankang
 * @version 2018-10-10
 */
@Service
@Transactional(readOnly = true)
public class MemberInfoService extends CrudService<MemberInfoDao, MemberInfo> {

    @Autowired
    private MemberInfoDao memberInfoDao;
    @Autowired
    private MemberFavoriteDao memberFavoriteDao;
    @Autowired
    private MemberFeedbackService memberFeedbackService;
    @Autowired
    private MemberFootprintDao memberFootprintDao;
    @Autowired
    private MemberMerchantCheckService memberMerchantCheckService;
    @Autowired
    private MemberRefereeIdMaxDao memberRefereeIdMaxDao;
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private SystemService systemService;

    public MemberInfo get(String id) {
        return super.get(id);
    }

    public List<MemberInfo> findList(MemberInfo memberInfo) {
        return super.findList(memberInfo);
    }

    public Page<MemberInfo> findPage(Page<MemberInfo> page, MemberInfo memberInfo) {
        return super.findPage(page, memberInfo);
    }

    public Page<MemberInfo> findCollectionPage(Page<MemberInfo> page, MemberInfo memberInfo) {
        memberInfo.setPage(page);
        page.setList(dao.findCollectionList(memberInfo));
        return page;
    }

    @Transactional(readOnly = false)
    public void save(MemberInfo memberInfo) {
        super.save(memberInfo);
    }

    @Transactional(readOnly = false)
    public void delete(MemberInfo memberInfo) {
        super.delete(memberInfo);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void memberCheck(MemberInfo memberInfo) {
        memberInfoDao.memberCheck(memberInfo);
    }

    public static boolean isPhone(String phone) throws ServiceException {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    public static boolean validatePayPasswordFormat(String payPassword) {
        String regex = "^\\d{6}$";
        if (StringUtils.isBlank(payPassword)) {
            return false;
        } else if (payPassword.length() != 6) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(payPassword);
            return m.matches();
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String genRefereeId() {
        List<MemberRefereeIdMax> memberRefereeIdMaxes = memberRefereeIdMaxDao.findList(new MemberRefereeIdMax());
        Integer maxId = 0;
        MemberRefereeIdMax memberRefereeIdMax;
        if (null == memberRefereeIdMaxes || memberRefereeIdMaxes.size() <= 0) {
            maxId = ++maxId;
            memberRefereeIdMax = new MemberRefereeIdMax();
            memberRefereeIdMax.setMaxRefereeId(maxId);
            memberRefereeIdMax.preInsert();
            memberRefereeIdMaxDao.insert(memberRefereeIdMax);
        } else {
            memberRefereeIdMax = memberRefereeIdMaxes.get(0);
            Random random = new Random();
            maxId = memberRefereeIdMax.getMaxRefereeId() + random.nextInt(3) + 1;
            memberRefereeIdMax.setMaxRefereeId(maxId);
            memberRefereeIdMax.preUpdate();
            memberRefereeIdMaxDao.update(memberRefereeIdMax);
        }
        return String.format("%06d", maxId);
    }

    public List<MemberFavorite> findList(MemberFavorite memberFavorite) {
        return memberFavoriteDao.findList(memberFavorite);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addFavorite(MemberFavorite memberFavorite) {
        memberFavorite.preInsert();
        memberFavoriteDao.insert(memberFavorite);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void deleteFavorite(MemberFavorite memberFavorite) {
        memberFavoriteDao.delete(memberFavorite);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void submitFeedback(MemberFeedback memberFeedback) {
        memberFeedbackService.save(memberFeedback);
    }

    public List<MemberFeedback> findList(MemberFeedback memberFeedback) {
        return memberFeedbackService.findList(memberFeedback);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addFootprint(MemberFootprint memberFootprint) {
        memberFootprint.preInsert();
        memberFootprintDao.insert(memberFootprint);
    }

    public List<MemberFootprint> findList(MemberFootprint memberFootprint) {
        return memberFootprintDao.findList(memberFootprint);
    }

    public String getPayPassword(String id) {
        return memberInfoDao.getPayPassword(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void savePayPassword(MemberInfo memberInfo) {
        memberInfoDao.savePayPassword(memberInfo);
    }

    /**
     * 验证会员支付密码
     *
     * @param payPassword 支付密码
     * @param memberCode  会员ID
     * @return 是否成功
     */
    public boolean validatePayPassword(String payPassword, String memberCode) {
        String cipherPayPassword = this.getPayPassword(memberCode);
        if (StringUtils.isBlank(payPassword) || !validatePayPasswordFormat(payPassword)) {
            return false;
        }
        return SystemService.validatePassword(payPassword, cipherPayPassword);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void submitMerchantData(MemberInfo memberInfo) {
        memberInfo.setStatus("3");
        memberInfoDao.updateMerchantData(memberInfo);
        MemberMerchantCheck memberMerchantCheck = new MemberMerchantCheck();
        memberMerchantCheck.setMerchantCode(memberInfo.getId());
        memberMerchantCheck.setStatus("0");
        memberMerchantCheckService.save(memberMerchantCheck);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void modifyMemberOperator(MemberInfo memberInfo) {
        memberInfoDao.modifyMemberOperator(memberInfo);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void bindWechat(MemberInfo memberInfo) {
        memberInfoDao.bindWechat(memberInfo);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void modifyAvatar(MemberInfo memberInfo) {
        memberInfoDao.modifyAvatar(memberInfo);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void modifyMerchantRefereeId(MemberInfo memberInfo) {
        memberInfoDao.modifyMerchantRefereeId(memberInfo);
    }

    /**
     * 取消商户认证 并下架所有商品
     *
     * @param id 商户ID
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void uncheckStatus(String id) {
        goodsInfoService.uncheckMerchant(id);
        memberInfoDao.uncheckStatus(id);
        User user = UserUtils.get(id);
        List<Role> roles = user.getRoleList();
        for (int i = 0; i < roles.size(); i++) {
            Role r = roles.get(i);
            if("1001".equals(r.getId()) || "1002".equals(r.getId())) {
                roles.remove(i);
            }
        }
        user.setRoleList(roles);
        systemService.saveUser(user);
    }

    public Page<MemberInfo> findListByPower(Page<MemberInfo> page, MemberInfo memberInfo) {
        memberInfo.setPage(page);
        memberInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u1"));
        return page.setList(memberInfoDao.findListByPower(memberInfo));
    }
}