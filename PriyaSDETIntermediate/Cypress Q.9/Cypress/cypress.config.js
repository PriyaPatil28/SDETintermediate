const { defineConfig } = require('cypress');

module.exports = defineConfig({
    e2e: {
        baseUrl: 'https://automationpanda.com',
        specPattern: 'cypress/integration/**/*.spec.js',
        supportFile: 'cypress/support/commands.js',
        setupNodeEvents(on, config) {
            // Optional Node events if needed later
        }
    },
    viewportWidth: 1280,
    viewportHeight: 800,
    video: false // Optional: disables video recording
});
