import Vue from 'vue'
import App from './App.vue'
import './styles/main.css';


// Font awesome installation
import {library} from '@fortawesome/fontawesome-svg-core';
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faCheck, faPlus, faTrash} from '@fortawesome/free-solid-svg-icons';
library.add(faPlus, faTrash, faCheck)
Vue.component('fa-icon', FontAwesomeIcon);


Vue.config.productionTip = false

//Custom vue directive to assist in dismissal of focused elements such as popups, inputs etc
Vue.directive('click-outside', {
  bind () {
    this.event = event => this.vm.$emit(this.expression, event)
    this.el.addEventListener('click', this.stopProp)
    document.body.addEventListener('click', this.event)
  },
  unbind() {
    this.el.removeEventListener('click', this.stopProp)
    document.body.removeEventListener('click', this.event)
  },

  stopProp(event) { event.stopPropagation() }
})


new Vue({
  render: h => h(App),
}).$mount('#app')
