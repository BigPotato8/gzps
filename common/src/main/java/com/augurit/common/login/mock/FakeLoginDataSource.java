package com.augurit.common.login.mock;

import com.augurit.agmobile.busi.common.login.model.LoginSettings;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.login.source.ILoginDataSource;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.realm.RealmList;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.busi.bpm.table.source
 * @createTime 创建时间 ：2018/5/22
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/5/22
 * @modifyMemo 修改备注：
 */

public class FakeLoginDataSource implements ILoginDataSource {
// 超级管理员: afcf4734-7a1a-4243-971e-4b91baa0984b
// 水务layertree :c8a80e3e-dc36-4be3-bc9a-b7aa3b321813
    private static final String FAKE_USER_JSON = "{\n" +
            "    \"creater\": \"\",\n" +
            "    \"createTime\": 1482116281000,\n" +
            "    \"modifier\": \"\",\n" +
            "    \"modifyTime\": 1482116281000,\n" +
            "    \"opusOmType\": \"USER\",\n" +
            "    \"id\": null,\n" +
            "    \"name\": null,\n" +
            "    \"pId\": null,\n" +
            "    \"pName\": null,\n" +
            "    \"nocheck\": false,\n" +
            "    \"open\": true,\n" +
            "    \"isHorizontal\": false,\n" +
            "    \"type\": null,\n" +
            "    \"isParent\": false,\n" +
            "    \"iconSkin\": null,\n" +
            "    \"userId\": \"c8a80e3e-dc36-4be3-bc9a-b7aa3b321813\",\n" +
            "    \"loginName\": \"111111\",\n" +
            "    \"loginPwd\": 111111,\n" +
            "    \"dingtalkAccount\": \"18680487286\",\n" +
            "    \"dingtalkId\": null,\n" +
            "    \"wechatAccount\": \"111111\",\n" +
            "    \"wechatId\": null,\n" +
            "    \"userName\": \"测试用户\",\n" +
            "    \"userSex\": \"0\",\n" +
            "    \"isPwdEncrypted\": null,\n" +
            "    \"encryptSalt\": \"\",\n" +
            "    \"pwdStrengthGrade\": null,\n" +
            "    \"isActive\": \"1\",\n" +
            "    \"activeBeginTime\": null,\n" +
            "    \"activeEndTime\": null,\n" +
            "    \"userDeleted\": \"0\",\n" +
            "    \"userNamePingyin\": null,\n" +
            "    \"userType\": null,\n" +
            "    \"userMobile\": null,\n" +
            "    \"userPostStatus\": null,\n" +
            "    \"orgId\": null,\n" +
            "    \"posId\": null,\n" +
            "    \"orgSeq\": null,\n" +
            "    \"isMain\": null,\n" +
            "    \"sortNo\": null,\n" +
            "    \"posNameStr\": null,\n" +
            "    \"userSig\": \"eJxlj11PgzAYhe-5FQ23M9oPyoaJFwQNMiVuY3PcNYwWVg1QSwd*xP*uMhdJfG*f5*Sc98MCANjr**Q8y-PmUBtm3pSwwSWwoX32B5WSnGWGEc3-QfGqpBYsK4zQA0SUUgzh2JFc1EYW8mS4CCN3xFv*zIaSI3a*0xTNPGesyHKA8c0miJbXXZCkoe9Ptss5jbsyXTm3ey2aehKWG-h4WL*gKMn2YhEGfVSu5r3GfSHQ*52-gFH-1FUkr8jDTpkL1TrRLubbad0Rk8ZXo0ojK-H7EXa9qUdn40Gd0K1s6kHAEFGECfw52-q0vgCYNly0\",\n" +
            "    \"opuOmId\": \"USER_018f96ee-cdea-492a-95a9-eac215fa0136\"\n" +
            "}";
    private static final String FAKE_ORG_JSON = "{\n" +
            "    \"creater\": \"韩冬\",\n" +
            "    \"createTime\": 1482116281000,\n" +
            "    \"modifier\": \"韩冬\",\n" +
            "    \"modifyTime\": 1482116281000,\n" +
            "    \"opusOmType\": \"ORG\",\n" +
            "    \"id\": null,\n" +
            "    \"name\": null,\n" +
            "    \"pId\": null,\n" +
            "    \"pName\": null,\n" +
            "    \"nocheck\": false,\n" +
            "    \"open\": true,\n" +
            "    \"isHorizontal\": false,\n" +
            "    \"type\": null,\n" +
            "    \"isParent\": false,\n" +
            "    \"iconSkin\": null,\n" +
            "    \"orgId\": \"150130\",\n" +
            "    \"orgCode\": \"150130\",\n" +
            "    \"orgName\": \"移动应用研发部\",\n" +
            "    \"orgShortName1\": \" \",\n" +
            "    \"orgShortName2\": \" \",\n" +
            "    \"parentOrgId\": \"150\",\n" +
            "    \"orgLevel\": 4,\n" +
            "    \"orgSeq\": \".A.150.150130.\",\n" +
            "    \"orgSortNo\": 2,\n" +
            "    \"isPublic\": \"1\",\n" +
            "    \"orgProperty\": \"d\",\n" +
            "    \"isActive\": \"1\",\n" +
            "    \"isLeaf\": \"1\",\n" +
            "    \"subUnitCount\": 1,\n" +
            "    \"subDeptCount\": 1,\n" +
            "    \"subGroupCount\": 0,\n" +
            "    \"subPosCount\": 0,\n" +
            "    \"subUserCount\": 0,\n" +
            "    \"orgType\": \" \",\n" +
            "    \"orgNature\": \" \",\n" +
            "    \"orgRank\": \" \",\n" +
            "    \"unitGbType\": \" \",\n" +
            "    \"unitGbCode\": \" \",\n" +
            "    \"unitGbSysCode\": \" \",\n" +
            "    \"orgFundsForm\": \" \",\n" +
            "    \"orgFoundTime\": null,\n" +
            "    \"orgLinkMan\": \" \",\n" +
            "    \"orgLinkTel\": \" \",\n" +
            "    \"orgZipCode\": \" \",\n" +
            "    \"orgEmail\": \" \",\n" +
            "    \"orgTel\": \" \",\n" +
            "    \"orgAddress\": \" \",\n" +
            "    \"orgWebsite\": \" \",\n" +
            "    \"orgWeibo\": \" \",\n" +
            "    \"orgApprovalNumber\": \" \",\n" +
            "    \"orgFunc\": \" \",\n" +
            "    \"orgDeleted\": \"0\",\n" +
            "    \"isTopOrg\": \"0\",\n" +
            "    \"opuOmId\": \"ORG_150130\"\n" +
            "}";
    private User mFakeUser;
    private Organization mFakeOrg;
    private LoginSettings mLoginSettings;

    public FakeLoginDataSource() {
        Gson gson = new Gson();
        mFakeUser = gson.fromJson(FAKE_USER_JSON, User.class);
        mFakeOrg = gson.fromJson(FAKE_ORG_JSON, Organization.class);
        RealmList<Organization> organizations = new RealmList<>();
        organizations.add(mFakeOrg);
        mFakeUser.setOrganizations(organizations);
        mFakeUser.setOrgSelected(0);
    }

    @Override
    public void saveUser(User user) {
        mFakeUser = user;
    }

    @Override
    public List<User> getAllUsers(boolean onlyShowInHistory) {
        return Collections.singletonList(mFakeUser);
    }

    @Override
    public User getLastUser(boolean onlyShowInHistory) {
        return mFakeUser;
    }

    @Override
    public void saveOrganizations(List<Organization> organizations) {

    }

    @Override
    public List<Organization> getAllOrganizations() {
        return Collections.singletonList(mFakeOrg);
    }

    @Override
    public List<Organization> getOrganizationsById(List<String> ids) {
        return Collections.singletonList(mFakeOrg);
    }

    @Override
    public void saveLoginSettings(LoginSettings settings) {
        mLoginSettings = settings;
    }

    @Override
    public LoginSettings getLoginSettings() {
        return mLoginSettings;
    }

    @Override
    public CharSequence getDeviceId() {
        return UUID.randomUUID().toString();
    }

}
