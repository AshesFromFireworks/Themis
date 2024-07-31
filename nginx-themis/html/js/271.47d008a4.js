"use strict";(self["webpackChunkthemis_vue"]=self["webpackChunkthemis_vue"]||[]).push([[271],{1669:function(r,t,e){e.r(t),e.d(t,{default:function(){return E}});var o=function(){var r=this;r._self._c;return r._m(0)},s=[function(){var r=this,t=r._self._c;return t("div",{staticClass:"right"},[t("div",{staticClass:"title"},[t("strong",[r._v("区块链信息")])]),t("br"),t("br"),t("br"),t("div",{staticClass:"box2"}),t("br"),t("br"),t("br")])}],n=e(5531),a={name:"DP-HistoryTrView",data(){return{}},methods:{hextoString(r){for(var t="",e=r.length/2,o=0;o<e;o++)t+=String.fromCharCode(parseInt(r.substr(2*o,2),16));return this.utf8to16(t)},utf8to16(r){var t,e,o,s,n,a;t="",o=r.length,e=0;while(e<o)switch(s=r.charCodeAt(e++),s>>4){case 0:case 1:case 2:case 3:case 4:case 5:case 6:case 7:t+=r.charAt(e-1);break;case 12:case 13:n=r.charCodeAt(e++),t+=String.fromCharCode((31&s)<<6|63&n);break;case 14:n=r.charCodeAt(e++),a=r.charCodeAt(e++),t+=String.fromCharCode((15&s)<<12|(63&n)<<6|63&a);break}return t}},mounted(){const r=new n.Ay$(new n.Ay$.providers.HttpProvider("HTTP://127.0.0.1:7545"));r.eth.getBlockNumber().then((t=>{t=Number(t);const e=document.querySelector(".box2");while(t>=0)r.eth.getBlock(t).then((t=>{if(void 0===t.transactions)return;const o=t.transactions;console.log(o),o.forEach((o=>{r.eth.getTransaction(o).then((r=>{if("0x"!==r.data){const o=r.data,s=this.hextoString(o.substring(2,o.length));try{const r=JSON.parse(s);e.innerHTML+=`<div class="historyTr" style="border: 1px solid black; border-radius: 5px;">\n                  <div class="head" style="border: 1px solid black; border-radius: 5px;"><span style="position: absolute; left:-84px;">区块哈希:</span>${t.hash}</div>\n                  <div class="body">\n                    <div style="border: 1px solid black; border-radius: 5px; margin: 5px 0;"><span style="position: absolute; left:-84px;">事务类型:</span>${r.type}</div>\n                    <div style="border: 1px solid black; border-radius: 5px; margin: 5px 0;"><span style="position: absolute; left:-57px;">参数1:</span>${r.para1}</div>\n                    <div style="border: 1px solid black; border-radius: 5px; margin: 5px 0;"><span style="position: absolute; left:-57px;">参数2:</span>${r.para2}</div>\n                    <div style="border: 1px solid black; border-radius: 5px; margin: 5px 0;"><span style="position: absolute; left:-48px;">签名:</span>${r.sign}</div>\n                  </div>\n                  <div class="trDate" style="border-radius: 5px; margin-bottom: 20px;">时间戳: ${r.ts}</div>\n                </div>`}catch{console.log(111)}}}))}))})),t--}))}},i=a,c=e(1656),d=(0,c.A)(i,o,s,!1,null,"eeb72112",null),E=d.exports},679:function(r,t,e){var o=e(1625),s=TypeError;r.exports=function(r,t){if(o(t,r))return r;throw new s("Incorrect invocation")}},5002:function(r){r.exports={IndexSizeError:{s:"INDEX_SIZE_ERR",c:1,m:1},DOMStringSizeError:{s:"DOMSTRING_SIZE_ERR",c:2,m:0},HierarchyRequestError:{s:"HIERARCHY_REQUEST_ERR",c:3,m:1},WrongDocumentError:{s:"WRONG_DOCUMENT_ERR",c:4,m:1},InvalidCharacterError:{s:"INVALID_CHARACTER_ERR",c:5,m:1},NoDataAllowedError:{s:"NO_DATA_ALLOWED_ERR",c:6,m:0},NoModificationAllowedError:{s:"NO_MODIFICATION_ALLOWED_ERR",c:7,m:1},NotFoundError:{s:"NOT_FOUND_ERR",c:8,m:1},NotSupportedError:{s:"NOT_SUPPORTED_ERR",c:9,m:1},InUseAttributeError:{s:"INUSE_ATTRIBUTE_ERR",c:10,m:1},InvalidStateError:{s:"INVALID_STATE_ERR",c:11,m:1},SyntaxError:{s:"SYNTAX_ERR",c:12,m:1},InvalidModificationError:{s:"INVALID_MODIFICATION_ERR",c:13,m:1},NamespaceError:{s:"NAMESPACE_ERR",c:14,m:1},InvalidAccessError:{s:"INVALID_ACCESS_ERR",c:15,m:1},ValidationError:{s:"VALIDATION_ERR",c:16,m:0},TypeMismatchError:{s:"TYPE_MISMATCH_ERR",c:17,m:1},SecurityError:{s:"SECURITY_ERR",c:18,m:1},NetworkError:{s:"NETWORK_ERR",c:19,m:1},AbortError:{s:"ABORT_ERR",c:20,m:1},URLMismatchError:{s:"URL_MISMATCH_ERR",c:21,m:1},QuotaExceededError:{s:"QUOTA_EXCEEDED_ERR",c:22,m:1},TimeoutError:{s:"TIMEOUT_ERR",c:23,m:1},InvalidNodeTypeError:{s:"INVALID_NODE_TYPE_ERR",c:24,m:1},DataCloneError:{s:"DATA_CLONE_ERR",c:25,m:1}}},6193:function(r,t,e){var o=e(9504),s=Error,n=o("".replace),a=function(r){return String(new s(r).stack)}("zxcasd"),i=/\n\s*at [^:]*:[^\n]*/,c=i.test(a);r.exports=function(r,t){if(c&&"string"==typeof r&&!s.prepareStackTrace)while(t--)r=n(r,i,"");return r}},3167:function(r,t,e){var o=e(4901),s=e(34),n=e(2967);r.exports=function(r,t,e){var a,i;return n&&o(a=t.constructor)&&a!==e&&s(i=a.prototype)&&i!==e.prototype&&n(r,i),r}},2603:function(r,t,e){var o=e(655);r.exports=function(r,t){return void 0===r?arguments.length<2?"":t:o(r)}},4979:function(r,t,e){var o=e(6518),s=e(4475),n=e(7751),a=e(6980),i=e(4913).f,c=e(9297),d=e(679),E=e(3167),l=e(2603),p=e(5002),u=e(6193),R=e(3724),_=e(6395),b="DOMException",h=n("Error"),m=n(b),v=function(){d(this,f);var r=arguments.length,t=l(r<1?void 0:arguments[0]),e=l(r<2?void 0:arguments[1],"Error"),o=new m(t,e),s=new h(t);return s.name=b,i(o,"stack",a(1,u(s.stack,1))),E(o,this,v),o},f=v.prototype=m.prototype,A="stack"in new h(b),x="stack"in new m(1,2),T=m&&R&&Object.getOwnPropertyDescriptor(s,b),I=!!T&&!(T.writable&&T.configurable),y=A&&!I&&!x;o({global:!0,constructor:!0,forced:_||y},{DOMException:y?v:m});var N=n(b),g=N.prototype;if(g.constructor!==N)for(var C in _||i(g,"constructor",a(1,N)),p)if(c(p,C)){var S=p[C],D=S.s;c(N,D)||i(N,D,a(6,S.c))}}}]);
//# sourceMappingURL=271.47d008a4.js.map