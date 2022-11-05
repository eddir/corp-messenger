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
		path: "/chats",
		name: "chats",
		meta: { layout: 'main' },
		component: () => import("@/views/Chats.vue"),
	},
	{
		path: "/profile",
		name: "profile",
		meta: { layout: 'main' },
		component: () => import("@/views/Profile.vue"),
	},
	{
		path: "/contacts",
		name: "contacts",
		meta: { layout: 'main' },
		component: () => import("@/views/Contacts.vue"),
	},
	{
		path: "/settings",
		name: "settings",
		meta: { layout: 'main' },
		component: () => import("@/views/Settings.vue"),
	},
	{
		path: "/",
		redirect: "/login"
	}
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

export default router;
