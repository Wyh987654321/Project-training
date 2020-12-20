import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import ElementUI, {Message} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import print from './print.js'
import  xlsx from 'xlsx'
import { Loading } from 'element-ui';

Vue.use(print)
Vue.prototype.$xlsx = xlsx
Vue.prototype.$Loading = Loading
Vue.prototype.$http = axios
//加密字符串
Vue.prototype.toCode =function (code){
  var c=String.fromCharCode(code.charCodeAt(0)+code.length);
  for(var i=1;i<code.length;i++)
  {
    c+=String.fromCharCode(code.charCodeAt(i)+code.charCodeAt(i-1));
  }
  return escape(c);
}
//解密字符串
Vue.prototype.toStr =function (code){
  code=unescape(code);
  var c=String.fromCharCode(code.charCodeAt(0)-code.length);
  for(var i=1;i<code.length;i++)
  {
    c+=String.fromCharCode(code.charCodeAt(i)-c.charCodeAt(i-1));
  }
  return unescape(c);
}

Vue.use(ElementUI);

Vue.prototype.$message =Message
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')