<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#editUserPwdForm').form({
            url : '${path }/user/editUserPwd',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
       <form id="editUserPwdForm" method="post" class="edit-user-pwd">
	      <div class="edit-user-pwd-title">
	          <span class="fi-torso" ></span>账户信息
	      </div>
	      <div class="wraper">
	          <table cellpadding="0" cellspacing="0" class="tg_m_pwtable">
	              <tbody>
	                  <tr>
	                      <td width="85" align="right">登录名称：</td>
	                      <td>
	                          <div class="edit-user-pwd-td txtdisabled">
	                              <input name="username" class="txtdisabled" type="text" id="username" readonly="readonly" value="<shiro:principal></shiro:principal>" datatype="*">
	                          </div>
	                      </td>
	                  </tr>
	                  <tr>
	                      <td align="right">
	                          <span class="red pad_r5">*</span>当前密码：
	                      </td>
	                      <td>
	                          <div class="edit-user-pwd-td">
	                              <input name="oldPwd" type="password" placeholder="请输入原密码" class="easyui-validatebox inputxt" data-options="required:true">
	                          </div>
	                      </td>
	                  </tr>
	                  <tr>
	                      <td align="right">
	                          <span class="red pad_r5">*</span>新的密码：
	                      </td>
	                      <td>
	                          <div class="edit-user-pwd-td">
	                              <input name="pwd" type="password" placeholder="请输入新密码" class="easyui-validatebox inputxt" data-options="required:true">
	                          </div>
	                      </td>
	                  </tr>
	                  <tr>
	                      <td align="right">
	                          <span class="red pad_r5">*</span>确认密码：
	                      </td>
	                      <td>
	                          <div class="edit-user-pwd-td">
	                              <input name="rePwd" type="password" placeholder="请再次输入新密码" class="easyui-validatebox inputxt" data-options="required:true,validType:'eqPwd[\'#editUserPwdForm input[name=pwd]\']'">
	                          </div>
	                      </td>
	                  </tr>
	              </tbody>
	          </table>
	      </div>
       </form>
    </div>
</div>