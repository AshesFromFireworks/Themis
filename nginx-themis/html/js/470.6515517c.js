"use strict";(self["webpackChunkthemis_vue"]=self["webpackChunkthemis_vue"]||[]).push([[470],{6470:function(t,e,s){s.r(e),s.d(e,{default:function(){return o}});var a=function(){var t=this,e=t._self._c;return e("div",{staticClass:"right"},[e("div",{staticClass:"box3"},[e("label",{staticClass:"crimeTypeLable",attrs:{for:"crimeType"}},[t._v("犯罪类型:")]),t._m(0),e("a",{attrs:{href:""}}),e("span",{staticClass:"span1"},[t._v("凭证：")]),e("input",{staticClass:"upload-inputText1",attrs:{type:"text"}}),e("span",{staticClass:"span2"},[t._v("私钥：")]),e("input",{staticClass:"upload-inputText2",attrs:{type:"text"}}),e("span",{staticClass:"crimePosition"},[t._v("犯罪地点：")]),e("input",{staticClass:"upload-crimePosition",attrs:{type:"text"}}),e("span",{staticClass:"crimeDescript"},[t._v("犯罪描述：")]),e("input",{staticClass:"upload-crimeDescript",attrs:{type:"text"}}),e("input",{staticClass:"form-submit",attrs:{type:"submit",value:"请求"},on:{click:t.queryTr}})])])},r=[function(){var t=this,e=t._self._c;return e("select",{staticClass:"crimeTypeSelect",attrs:{id:"crimeType",name:"crimeType"}},[e("option",{attrs:{value:"偷窃"}},[t._v("偷窃")]),e("option",{attrs:{value:"抢劫"}},[t._v("抢劫")])])}],i=s(8355),u={name:"DP-NewTr",data(){return{}},methods:{queryTr(){(0,i.A)({url:"/api/request",method:"post",headers:{token:sessionStorage.getItem("token")},data:{id:this.$parent.userId,cre:document.querySelector(".upload-inputText1").value,prk:document.querySelector(".upload-inputText2").value,type:document.querySelector(".crimeTypeSelect").value,spot:document.querySelector(".upload-crimePosition").value,desc:document.querySelector(".upload-crimeDescript").value}}).then((t=>{console.log(t)})).catch((t=>{console.log(t)}))}}},c=u,l=s(1656),n=(0,l.A)(c,a,r,!1,null,null,null),o=n.exports}}]);
//# sourceMappingURL=470.6515517c.js.map