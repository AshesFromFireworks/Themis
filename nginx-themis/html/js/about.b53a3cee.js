"use strict";(self["webpackChunkthemis_vue"]=self["webpackChunkthemis_vue"]||[]).push([[594],{5908:function(e,t,s){s.r(t),s.d(t,{default:function(){return u}});var a=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticClass:"themis"},[e._v("Themis")]),t("div",{staticClass:"box"},[t("h1",[e._v("用户登录")]),t("div",[t("input",{staticClass:"username",attrs:{type:"text",placeholder:"请输入用户名"}}),t("br"),t("input",{staticClass:"password",attrs:{type:"password",placeholder:"请输入密码"}}),t("button",{staticClass:"submit",on:{click:e.login}},[e._v("登录")])]),t("div",[t("router-link",{staticClass:"sign",attrs:{to:"/dp_register"}},[e._v("数据提供者注册")]),t("router-link",{staticClass:"in_register",attrs:{to:"/in_register"}},[e._v("调查员注册")]),t("router-link",{staticClass:"forgetpassword",attrs:{to:"/forgetPassword"}},[e._v("忘记密码")])],1)])])},r=[],n=(s(4114),s(8355)),i={name:"LoginView",data(){return{}},methods:{login(){const e=document.querySelector(".username").value,t=document.querySelector(".password").value;(0,n.A)({method:"post",url:"/api/login",data:{username:e,password:t}}).then((e=>{const t=e.data.code;0==t?this.$message.error(e.data.msg):1==t?(sessionStorage.setItem("token",e.data.data.token),sessionStorage.setItem("userId",e.data.data.id),sessionStorage.setItem("userName",e.data.data.username),this.$router.push("/dp_index"),this.$message({showClose:!0,message:"数据提供者登陆成功",type:"success"})):2==t&&(sessionStorage.setItem("token",e.data.data.token),sessionStorage.setItem("userId",e.data.data.id),this.$router.push("/in_index"),this.$message({showClose:!0,message:"调查员登陆成功",type:"success"}))})).catch((e=>{console.log(e),this.$message.error("登陆失败")}))}}},o=i,l=s(1656),c=(0,l.A)(o,a,r,!1,null,"1393c1c9",null),u=c.exports},8764:function(e,t,s){s.r(t),s.d(t,{default:function(){return u}});var a=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticClass:"left"},[t("div",{staticClass:"title"},[e._v("Themis")]),t("ul",{staticClass:"menu"},[e._m(0),t("li",{staticClass:"else",on:{click:e.changeLinkStyle}},[t("router-link",{staticClass:"span",attrs:{to:"/dp_index/introduction"}},[e._v("Themis简介")])],1),t("li",{staticClass:"else",on:{click:e.changeLinkStyle}},[t("router-link",{staticClass:"span",attrs:{to:"/dp_index/new_tr"}},[e._v("新建事务")])],1),t("li",{staticClass:"else",on:{click:e.changeLinkStyle}},[t("router-link",{staticClass:"span",attrs:{to:"/dp_index/history_tr"}},[e._v("历史事务")])],1),t("li",{staticClass:"else",on:{click:e.changeLinkStyle}},[t("router-link",{staticClass:"span",attrs:{to:"/dp_index/user_data"}},[e._v("个人资料")])],1)])]),t("div",{staticClass:"top"},[t("span",{staticClass:"username"},[e._v(e._s(e.userName))])]),t("router-view")],1)},r=[function(){var e=this,t=e._self._c;return t("li",{staticClass:"gongneng"},[t("span",{staticClass:"span"},[e._v("功能")])])}],n=(s(4114),s(8355)),i={name:"DP-Index",data(){return{userId:sessionStorage.getItem("userId"),userName:"XXXXXX",email:"XXXXXXXXX@XX.XXX",registerTime:"XXXX-XX-XX XX:XX:XX"}},methods:{changeLinkStyle(e){const t=document.querySelector(".active");null!==t&&t.classList.remove("active"),e.target.parentNode.classList.add("active")}},created(){(0,n.A)({method:"get",url:"/api/dp/info/"+this.userId,headers:{token:sessionStorage.getItem("token")}}).then((e=>{console.log(e.data),0==e.data.code&&(this.$message.error("用户异常，请重新登录"),this.$router.push("/")),this.userId=e.data.data.id,this.userName=e.data.data.username,this.email=e.data.data.email,this.registerTime=e.data.data.registerTime.replace("T"," ")})).catch((()=>{this.$message.error("用户异常，请重新登录"),this.$router.push("/")}))}},o=i,l=s(1656),c=(0,l.A)(o,a,r,!1,null,"32bf7b62",null),u=c.exports},3091:function(e,t,s){s.r(t),s.d(t,{default:function(){return u}});var a=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticClass:"container"},[t("h3",[e._v("新用户注册")]),t("span",{staticClass:"skip"},[e._v("已有账号？"),t("router-link",{staticClass:"a",attrs:{to:"/"}},[e._v("立即登录")])],1),t("div",{staticClass:"form-item",attrs:{"data-prop":"username"}},[t("input",{attrs:{name:"username",type:"text",placeholder:"设置用户名称",autocomplete:"off"},on:{change:e.verifyName}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item",attrs:{"data-prop":"email"}},[t("input",{attrs:{name:"email",type:"text",placeholder:"输入用户邮箱"},on:{change:e.verifyEmail}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item",attrs:{"data-prop":"password"}},[t("input",{attrs:{name:"password",type:"password",placeholder:"请输入密码"},on:{change:e.verifyPwd}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item",attrs:{"data-prop":"confirm"}},[t("input",{attrs:{name:"confirm",type:"password",placeholder:"请确认密码"},on:{change:e.verifyConfirm}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item"},[t("button",{staticClass:"submit",on:{click:e.userRegister}},[e._v("下一步")])])])])},r=[],n=(s(4114),s(8355)),i={name:"DP-RegisterView",data(){return{}},methods:{verifyName(){const e=document.querySelector("[name=username]"),t=e.nextElementSibling,s=/^[a-zA-Z0-9-_]{6,15}$/;return s.test(e.value)||""==e.value?(t.innerText="",!0):(t.innerText="输入不合法,6~15位字母数字下划线组成",!1)},verifyEmail(){const e=document.querySelector("[name=email]"),t=e.nextElementSibling,s=/^\w{3,}(\.\w+)*@[A-z0-9]+(\.[A-z]{2,5}){1,2}$/;return s.test(e.value)||""==e.value?(t.innerText="",!0):(t.innerText="输入邮箱不合法",!1)},verifyPwd(){const e=document.querySelector("[name=password]"),t=e.nextElementSibling,s=/^[a-zA-Z0-9-_]{6,16}$/;return s.test(e.value)||""==e.value?(t.innerText="",!0):(t.innerText="输入不合法,6~16位数字字母符号组成",!1)},verifyConfirm(){const e=document.querySelector("[name=confirm]"),t=e.nextElementSibling;return e.value!==document.querySelector("[name=password]").value&&""!=e.value?(t.innerText="两次密码输入不一致",!1):(t.innerText="",!0)},userRegister(){if(this.verifyName()&&this.verifyEmail()&&this.verifyPwd()&&this.verifyConfirm()){const e={username:document.querySelector("[name=username]").value,email:document.querySelector("[name=email]").value,password:document.querySelector("[name=password]").value};(0,n.A)({method:"post",url:"/api/dp/register",data:e}).then((t=>{const s=t.data.code;0==s?this.$message.error(t.data.msg):1==s&&(console.log(t.data),sessionStorage.setItem("email",t.data.data.email),sessionStorage.setItem("username",t.data.data.username),sessionStorage.setItem("password",t.data.data.password),sessionStorage.setItem("cre1",t.data.data.cre),(0,n.A)({method:"post",url:"/api2/dp/register",data:e}).then((e=>{const t=e.data.code;0==t?this.$message.error("注册失败"):1==t&&(console.log(e.data),sessionStorage.setItem("cre2",e.data.data.cre),this.$router.push("/dp_transfer"),this.$message({showClose:!0,message:"注册成功",type:"success"}))})))})).catch((()=>{this.$message.error("注册失败")}))}}}},o=i,l=s(1656),c=(0,l.A)(o,a,r,!1,null,null,null),u=c.exports},6627:function(e,t,s){s.r(t),s.d(t,{default:function(){return u}});var a=function(){var e=this,t=e._self._c;return t("div",{staticClass:"box"},[t("h1",[e._v("分布式匿名凭证生成")]),t("div",[e._m(0),t("br"),t("button",{staticClass:"submit",on:{click:function(t){return e.generateDAC()}}},[e._v("生成")])]),t("div",[t("router-link",{staticClass:"forgetpassword",attrs:{to:"/"}},[e._v("我已保存")])],1)])},r=[function(){var e=this,t=e._self._c;return t("div",[t("textarea",{staticClass:"password",attrs:{cols:"60px",rows:"10px",placeholder:"                     凭证请妥善保管",name:"cre"}})])}],n=s(8355),i={name:"DP-Transfer",data(){return{}},methods:{generateDAC(){(0,n.A)({url:"/api/dp/credit",method:"post",data:{cre1:sessionStorage.getItem("cre1"),cre2:sessionStorage.getItem("cre2"),username:sessionStorage.getItem("username"),password:sessionStorage.getItem("password"),email:sessionStorage.getItem("email")}}).then((e=>{console.log(e.data.data),document.querySelector(".password").value=e.data.data.cre})).catch((()=>{this.$message.error("生成凭证失败")}))}}},o=i,l=s(1656),c=(0,l.A)(o,a,r,!1,null,"a4c770ba",null),u=c.exports},8032:function(e,t,s){s.r(t),s.d(t,{default:function(){return u}});var a=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticClass:"left"},[t("div",{staticClass:"title"},[e._v("Themis")]),t("ul",{staticClass:"menu"},[e._m(0),t("li",{staticClass:"else",on:{click:e.changeLinkStyle}},[t("router-link",{staticClass:"span",attrs:{to:"/in_index/introduction"}},[e._v("Themis简介")])],1),t("li",{staticClass:"else",on:{click:e.changeLinkStyle}},[t("router-link",{staticClass:"span",attrs:{to:"/in_index/new_tr"}},[e._v("请求事务")])],1),t("li",{staticClass:"else",on:{click:e.changeLinkStyle}},[t("router-link",{staticClass:"span",attrs:{to:"/in_index/history_tr"}},[e._v("历史事务")])],1),t("li",{staticClass:"else",on:{click:e.changeLinkStyle}},[t("router-link",{staticClass:"span",attrs:{to:"/in_index/user_data"}},[e._v("个人资料")])],1)])]),t("div",{staticClass:"top"},[t("span",{staticClass:"username"},[e._v(e._s(e.userName))])]),t("router-view")],1)},r=[function(){var e=this,t=e._self._c;return t("li",{staticClass:"gongneng"},[t("span",{staticClass:"span"},[e._v("功能")])])}],n=(s(4114),s(8355)),i={name:"IN-Index",data(){return{userId:sessionStorage.getItem("userId"),userName:"XXXXXX",email:"XXXXXXXXX@XX.XXX",registerTime:"XXXX-XX-XX XX:XX:XX"}},methods:{changeLinkStyle(e){const t=document.querySelector(".active");null!==t&&t.classList.remove("active"),e.target.parentNode.classList.add("active")}},created(){(0,n.A)({method:"get",url:"/api/in/info/"+this.userId,headers:{token:sessionStorage.getItem("token")}}).then((e=>{console.log(e.data),0==e.data.code&&(this.$message.error("用户异常，请重新登录"),this.$router.push("/")),this.userId=e.data.data.id,this.userName=e.data.data.username,this.email=e.data.data.email,this.registerTime=e.data.data.registerTime.replace("T"," ")})).catch((()=>{this.$message.error("用户异常，请重新登录"),this.$router.push("/")}))}},o=i,l=s(1656),c=(0,l.A)(o,a,r,!1,null,"795d2a43",null),u=c.exports},542:function(e,t,s){s.r(t),s.d(t,{default:function(){return u}});var a=function(){var e=this,t=e._self._c;return t("div",{staticClass:"container"},[t("h3",[e._v("调查员注册")]),t("span",{staticClass:"skip"},[e._v("已有账号？"),t("router-link",{staticClass:"a",attrs:{to:"/"}},[e._v("立即登录")])],1),t("div",{staticClass:"form-item left",attrs:{"data-prop":"username"}},[t("input",{attrs:{name:"username",type:"text",placeholder:"设置用户名称",autocomplete:"off"},on:{change:e.verifyName}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item left",attrs:{"data-prop":"email"}},[t("input",{attrs:{name:"email",type:"text",placeholder:"输入用户邮箱"},on:{change:e.verifyEmail}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item left",attrs:{"data-prop":"password"}},[t("input",{attrs:{name:"password",type:"password",placeholder:"设置6至20位字母、数字和符号组合"},on:{change:e.verifyPwd}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item left",attrs:{"data-prop":"confirm"}},[t("input",{attrs:{name:"confirm",type:"password",placeholder:"请再次输入上面密码"},on:{change:e.verifyConfirm}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item right",attrs:{"data-prop":"value1"}},[t("input",{attrs:{name:"value1",type:"password",placeholder:"属性1"},on:{change:e.verifyValue1}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item right",attrs:{"data-prop":"value2"}},[t("input",{attrs:{name:"value2",type:"password",placeholder:"属性2"},on:{change:e.verifyValue2}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item right",attrs:{"data-prop":"value3"}},[t("input",{attrs:{name:"value3",type:"password",placeholder:"属性3"},on:{change:e.verifyValue3}}),t("span",{staticClass:"msg"})]),t("div",{staticClass:"form-item button"},[t("button",{staticClass:"submit",on:{click:e.userRegister}},[e._v("下一步")])])])},r=[],n=(s(4114),s(8355)),i={name:"IN-RegisterView",data(){return{}},methods:{verifyName(){const e=document.querySelector("[name=username]"),t=e.nextElementSibling,s=/^[a-zA-Z0-9-_]{6,15}$/;return s.test(e.value)||""==e.value?(t.innerText="",!0):(t.innerText="输入不合法,6~15位字母数字下划线组成",!1)},verifyEmail(){const e=document.querySelector("[name=email]"),t=e.nextElementSibling,s=/^\w{3,}(\.\w+)*@[A-z0-9]+(\.[A-z]{2,5}){1,2}$/;return s.test(e.value)||""==e.value?(t.innerText="",!0):(t.innerText="输入邮箱不合法",!1)},verifyPwd(){const e=document.querySelector("[name=password]"),t=e.nextElementSibling,s=/^[a-zA-Z0-9-_]{6,16}$/;return s.test(e.value)||""==e.value?(t.innerText="",!0):(t.innerText="输入不合法,6~16位数字字母符号组成",!1)},verifyConfirm(){const e=document.querySelector("[name=confirm]"),t=e.nextElementSibling;return e.value!==document.querySelector("[name=password]").value&&""!=e.value?(t.innerText="两次密码输入不一致",!1):(t.innerText="",!0)},verifyValue1(){const e=document.querySelector("[name=value1]"),t=e.nextElementSibling;return""===e.value?(t.innerText="属性不能为空",!1):(t.innerText="",!0)},verifyValue2(){const e=document.querySelector("[name=value2]"),t=e.nextElementSibling;return""===e.value?(t.innerText="属性不能为空",!1):(t.innerText="",!0)},verifyValue3(){const e=document.querySelector("[name=value3]"),t=e.nextElementSibling;return""===e.value?(t.innerText="属性不能为空",!1):(t.innerText="",!0)},userRegister(){if(this.verifyName()&&this.verifyEmail()&&this.verifyPwd()&&this.verifyConfirm()&&this.verifyValue1()&&this.verifyValue2()&&this.verifyValue3()){const e={username:document.querySelector("[name=username]").value,email:document.querySelector("[name=email]").value,password:document.querySelector("[name=password]").value,attr1:document.querySelector("[name=value1]").value,attr2:document.querySelector("[name=value2]").value,attr3:document.querySelector("[name=value3]").value};(0,n.A)({method:"post",url:"/api/in/register",data:e}).then((t=>{const s=t.data.code;0==s?this.$message.error("注册失败"):1==s&&(sessionStorage.setItem("email",t.data.data.email),sessionStorage.setItem("username",t.data.data.username),sessionStorage.setItem("password",t.data.data.password),sessionStorage.setItem("cre1",t.data.data.cre),sessionStorage.setItem("prk",t.data.data.prk),(0,n.A)({method:"post",url:"/api2/in/register",data:e}).then((e=>{const t=e.data.code;0==t?this.$message.error("注册失败"):1==t&&(sessionStorage.setItem("cre2",e.data.data.cre),this.$router.push("/in_transfer"),this.$message({showClose:!0,message:"注册成功",type:"success"}))})))})).catch((()=>{this.$message.error("注册失败")}))}}}},o=i,l=s(1656),c=(0,l.A)(o,a,r,!1,null,"38d7f12e",null),u=c.exports},1036:function(e,t,s){s.r(t),s.d(t,{default:function(){return d}});var a=function(){var e=this,t=e._self._c;return t("div",{staticClass:"box"},[t("h1",[e._v("分布式匿名凭证生成")]),t("div",[e._m(0),t("button",{staticClass:"submit1",on:{click:function(t){return e.generateDAC()}}},[e._v("生成")])]),t("div",[t("button",{staticClass:"transfer displayNone",on:{click:function(t){return e.toLoginIndex()}}},[e._v("我已保存，返回登录页面")])])])},r=[function(){var e=this,t=e._self._c;return t("div",[t("span",{staticClass:"cre-inf displayNone"},[e._v("您的凭证(请妥善保存)")]),t("textarea",{staticClass:"cre displayNone",attrs:{name:"cre",placeholder:"123456789"}}),t("span",{staticClass:"prk-inf displayNone"},[e._v("您的密钥(请妥善保存)")]),t("textarea",{staticClass:"prk displayNone",attrs:{name:"cre",placeholder:"123456789"}})])}],n=(s(4114),s(8355)),i=s(5531),o={name:"IN-Transfer",data(){return{}},methods:{generateDAC(){(0,n.A)({url:"/api/in/credit",method:"post",data:{cre1:sessionStorage.getItem("cre1"),cre2:sessionStorage.getItem("cre2"),username:sessionStorage.getItem("username"),password:sessionStorage.getItem("password"),email:sessionStorage.getItem("email")}}).then((e=>{document.querySelector(".submit1").classList.add("displayNone"),document.querySelector(".cre-inf").classList.remove("displayNone"),document.querySelector(".prk-inf").classList.remove("displayNone"),document.querySelector(".transfer").classList.remove("displayNone");const t=document.querySelector(".prk"),s=document.querySelector(".cre");t.classList.remove("displayNone"),s.classList.remove("displayNone"),s.value=e.data.data.cre,t.value=e.data.data.cre,this.uploadBlock({type:"type",para1:"para1",para2:"para2",ts:"2024-05-10",sign:"sign"})})).catch((()=>{this.$message.error("生成凭证失败")}))},toLoginIndex(){this.$router.push("/")},uploadBlock(e){const t=new i.Ay$(i.Ay$.givenProvider||"ws://localhost:7545");t.eth.sendTransaction({from:"0xA6a48778Ff91807DAA75DaE07F5e13b8B9000996",to:"0x0043A755399603686567334bD4F1F507F8647E03",value:1e15,gas:121e3,data:this.stringtoHex(JSON.stringify(e))}).then((function(e){console.log("交易状态：",e.status)}))},stringtoHex(e){for(var t="0x",s=0;s<e.length;s++)""==t?t=e.charCodeAt(s).toString(16):t+=e.charCodeAt(s).toString(16);return console.log(t),t}}},l=o,c=s(1656),u=(0,c.A)(l,a,r,!1,null,"a0d447d6",null),d=u.exports}}]);
//# sourceMappingURL=about.b53a3cee.js.map