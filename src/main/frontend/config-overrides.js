module.exports = {
    webpack: function(config, env) {
        config.optimization.runtimeChunk = false;
        config.optimization.splitChunks = {
            cacheGroups: {
                default: false,
            },
        };
        config.entry = {
            main: './src/index.tsx',
            signIn: './src/indexSignIn.tsx',
            signUp: './src/indexSignUp.tsx'
        }
        return config;
    }
}
