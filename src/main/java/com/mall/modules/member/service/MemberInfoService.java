package com.mall.modules.member.service;

import com.mall.common.persistence.Page;
import com.mall.common.service.CrudService;
import com.mall.common.service.ServiceException;
import com.mall.modules.member.dao.MemberInfoDao;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.utils.Base32;
import com.sohu.idcenter.IdWorker;
import org.apache.fop.util.LogUtil;
import org.springframework.stereotype.Service;
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

    public MemberInfo get(String id) {
        return super.get(id);
    }

    public List<MemberInfo> findList(MemberInfo memberInfo) {
        return super.findList(memberInfo);
    }

    public Page<MemberInfo> findPage(Page<MemberInfo> page, MemberInfo memberInfo) {
        return super.findPage(page, memberInfo);
    }

    @Transactional(readOnly = false)
    public void save(MemberInfo memberInfo) {
        super.save(memberInfo);
    }

    @Transactional(readOnly = false)
    public void delete(MemberInfo memberInfo) {
        super.delete(memberInfo);
    }

    public String genRefereeCode() {
        return "";
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


    public static String genRefereeId() {
        IdWorker idWorker = new IdWorker();

        Random random = new Random();
        int max = 8;
        int min = 1;
        int pwdLength = 13;
        String timeStamp = String.valueOf(idWorker.getId());
        String randomStr = String.valueOf(random.nextInt(max) % (max - min + 1) + min);
        String encodeStr = randomStr + timeStamp;
        String couponPwd = Base32.encode(Long.valueOf(encodeStr));
        if (couponPwd.length() != pwdLength) {
            couponPwd = genRefereeId();
        }
        return couponPwd;
    }


}