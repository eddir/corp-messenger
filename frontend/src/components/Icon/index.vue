<template>
    <svg :style="styles" :class="classes">
        <use :xlink:href="xlinkHref" />
    </svg>
</template>

<script>
export default {
	props: {
		name: {
			type: String,
			required: true
		},
		size: {
			type: String,
			default: '16px'
		},
		width: String,
		height: String
	},
	data () {
		return {}
    },
	computed: {
		xlinkHref () {
			return `#${this.name}`
		},
		styles () {
			var width = this.width || this.size,
				height =  this.height || this.size,
				style = {};

			if (width) {
				style.width = width;
			}

			if (height) {
				style.height = height;
			}

			return style;
		},
		classes () {
			return {
				'icon': true
			}
		}
	}
}
</script>
<style lang="less" scoped>
    .icon {
		flex-shrink: 0;
		pointer-events: none;
	}
</style>
