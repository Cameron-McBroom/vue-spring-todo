import Vue from 'vue'
import Router from 'vue-router'
import TodoListPage from "@/routes/TodoListPage";

// import store from './store'

Vue.use(Router);

const router = new Router({
    mode: 'history',
    routes: [
        { path: '/', component: TodoListPage },
    ]
});

// router.beforeEach((to, from, next) => {
//     if (to.matched.some(record => record.meta.requiresAuth)) {
//         // this route requires auth, check if logged in
//         // if not, redirect to login page.
//         if (!store.getters.isLoggedIn) {
//             next({
//                 path: '/login'
//             })
//         } else {
//             next();
//         }
//     } else {
//         next(); // make sure to always call next()!
//     }
// });

export default router;