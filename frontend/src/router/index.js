import { createRouter, createWebHistory } from "vue-router";

const routes = [
	{
		path: "/login",
		name: "login",
		meta: { layout: 'auth' },
		component: () => import("@/views/Login.vue"),
	},
	{
		path: "/register",
		name: "register",
		meta: { layout: 'auth' },
		component: () => import("@/views/Register.vue"),
	},
	{
		path: "/",
		name: "home",
		meta: { layout: 'main' },
		component: () => import("@/views/Home.vue"),
	},
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

export default router;
