module.exports = {
    root: true,
    env: {
        node: true
    },
    'extends': [
        'plugin:vue/recommended',
        'eslint:recommended'
    ],
    'rules': {
        'no-console':
            process.env.NODE_ENV === 'production' ? 'error' : 'off',
        'no-debugger':
            process.env.NODE_ENV === 'production' ? 'error' : 'off'
    },
    'parserOptions': {
        'parser': "@babel/eslint-parser",
        'ecmaVersion': 2018,
        'sourceType': 'module',
        'requireConfigFile': false,
    },
}