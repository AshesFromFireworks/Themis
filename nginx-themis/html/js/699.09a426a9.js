(self["webpackChunkthemis_vue"]=self["webpackChunkthemis_vue"]||[]).push([[699],{5699:function(t,e,a){"use strict";a.r(e),a.d(e,{default:function(){return l}});var o=function(){var t=this,e=t._self._c;return e("div",{staticClass:"right"},[e("div",{staticClass:"box3"},[e("label",{staticClass:"crimeTypeLable",attrs:{for:"crimeType"}},[t._v("犯罪类型:")]),t._m(0),e("a",{attrs:{href:"",download:""}}),e("span",{staticClass:"span1"},[t._v("凭证：")]),e("input",{staticClass:"upload-inputText1",attrs:{type:"text"}}),e("span",{staticClass:"span2"},[t._v("私钥：")]),e("input",{staticClass:"upload-inputText2",attrs:{type:"text"}}),e("span",{staticClass:"crimePosition"},[t._v("犯罪地点：")]),e("input",{staticClass:"upload-crimePosition",attrs:{type:"text"}}),e("span",{staticClass:"crimeDescript"},[t._v("犯罪描述：")]),e("input",{staticClass:"upload-crimeDescript",attrs:{type:"text"}}),e("input",{staticClass:"form-submit",attrs:{type:"submit",value:"请求"},on:{click:t.queryTr}})])])},s=[function(){var t=this,e=t._self._c;return e("select",{staticClass:"crimeTypeSelect",attrs:{id:"crimeType",name:"crimeType"}},[e("option",{attrs:{value:"偷窃"}},[t._v("偷窃")]),e("option",{attrs:{value:"抢劫"}},[t._v("抢劫")]),e("option",{attrs:{value:"强奸"}},[t._v("强奸")]),e("option",{attrs:{value:"谋杀"}},[t._v("谋杀")])])}],i=a(5531),r=a(8355),p={name:"DP-NewTr",data(){return{}},methods:{queryTr(){(0,r.A)({url:"/api/request",method:"post",headers:{token:sessionStorage.getItem("token")},data:{id:sessionStorage.getItem("userId"),cre:document.querySelector(".upload-inputText1").value,prk:document.querySelector(".upload-inputText2").value,type:document.querySelector(".crimeTypeSelect").value,spot:document.querySelector(".upload-crimePosition").value,desc:document.querySelector(".upload-crimeDescript").value}}).then((t=>{const e=t.data.code;if(0==e)this.$message.error("请求失败");else{const e=t.data.data.filepath;console.log(e),document.querySelector(".box3 a").href="/api/load/"+e,document.querySelector(".box3 a").download=e,document.querySelector(".box3 a").click();try{this.uploadBlock({type:t.data.data.tx,para1:t.data.data.para1,para2:t.data.data.para2,ts:t.data.data.ts,sign:t.data.data.sign})}catch{this.$message.error("上传区块失败")}}})).catch((()=>{this.$message.error("请求失败")}))},uploadBlock(t){const e=new i.Ay$(new i.Ay$.providers.HttpProvider("HTTP://127.0.0.1:7545"));e.eth.sendTransaction({from:"0x24F006B75F942466DCFD24Ba80a5E53744774B4C",to:"0xc2cC927D5865e02c3aDBc86687f2c2fFbEBfc687",value:1e15,gas:121e3,data:this.stringtoHex(JSON.stringify(t))}).then((function(t){console.log("交易状态：",t.status)}))},stringtoHex(t){for(var e="0x",a=0;a<t.length;a++)""==e?e=t.charCodeAt(a).toString(16):e+=t.charCodeAt(a).toString(16);return console.log(e),e}}},n=p,c=(a(7526),a(7678),a(1656)),d=(0,c.A)(n,o,s,!1,null,"63709839",null),l=d.exports},8223:function(t,e,a){"use strict";a.r(e);var o=a(1601),s=a.n(o),i=a(6314),r=a.n(i),p=a(4417),n=a.n(p),c=new URL(a(3665),a.b),d=r()(s()),l=n()(c);d.push([t.id,"body{background:url("+l+") no-repeat;background-size:cover;background-attachment:fixed}",""]),e["default"]=d},4039:function(t,e,a){"use strict";a.r(e);var o=a(1601),s=a.n(o),i=a(6314),r=a.n(i),p=r()(s());p.push([t.id,".box3[data-v-63709839]{position:absolute;left:300px;top:80px;width:1000px;height:650px;background-size:cover;border:2px solid #000;border-radius:50px}.box3 .span1[data-v-63709839]{top:98px}.box3 .span1[data-v-63709839],.box3 .span2[data-v-63709839]{position:absolute;left:130px;font-size:18px}.box3 .span2[data-v-63709839]{top:177px}.right .box3 .upload-inputText1[data-v-63709839]{top:95px}.right .box3 .upload-inputText1[data-v-63709839],.right .box3 .upload-inputText2[data-v-63709839]{position:absolute;left:180px;width:600px;height:30px;border-radius:10px}.right .box3 .upload-inputText2[data-v-63709839]{top:175px}.box3 .crimeTypeLable[data-v-63709839]{position:absolute;left:350px;top:250px;font-size:18px}.box3 .crimeTypeSelect[data-v-63709839]{position:absolute;left:450px;top:248px;width:80px;height:30px;padding-left:5px;font-size:18px;border-radius:10px}.box3 .crimePosition[data-v-63709839]{top:308px}.box3 .crimeDescript[data-v-63709839],.box3 .crimePosition[data-v-63709839]{position:absolute;left:95px;font-size:18px}.box3 .crimeDescript[data-v-63709839]{top:396px}.box3 .upload-crimePosition[data-v-63709839]{top:305px}.box3 .upload-crimeDescript[data-v-63709839],.box3 .upload-crimePosition[data-v-63709839]{position:absolute;left:180px;width:600px;height:30px;border-radius:10px}.box3 .upload-crimeDescript[data-v-63709839]{top:395px}.right .box3 .form-submit[data-v-63709839]{position:absolute;left:350px;top:500px;background-color:#23c6c8;display:inline-block;width:250px;height:80px;overflow:hidden;text-align:center;font-size:50px;vertical-align:center;border:1px solid #23c6c8;color:#fff;border-radius:15px}",""]),e["default"]=p},7526:function(t,e,a){var o=a(8223);o.__esModule&&(o=o.default),"string"===typeof o&&(o=[[t.id,o,""]]),o.locals&&(t.exports=o.locals);var s=a(8459).A;s("e7e3a86a",o,!0,{sourceMap:!1,shadowMode:!1})},7678:function(t,e,a){var o=a(4039);o.__esModule&&(o=o.default),"string"===typeof o&&(o=[[t.id,o,""]]),o.locals&&(t.exports=o.locals);var s=a(8459).A;s("da589b52",o,!0,{sourceMap:!1,shadowMode:!1})},3665:function(t,e,a){"use strict";t.exports=a.p+"img/R-C.e1d8f0a7.jpg"}}]);
//# sourceMappingURL=699.09a426a9.js.map