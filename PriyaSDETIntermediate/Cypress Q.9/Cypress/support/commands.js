// Custom command to visit the blog post page
Cypress.Commands.add('visitDemoSitesPost', () => {
    cy.visit('/2021/12/29/want-to-practice-test-automation-try-these-demo-sites/');
});

// Custom command to click on the "Speaking" link
Cypress.Commands.add('clickSpeakingLink', () => {
    cy.contains('Speaking').click();
});

// Custom command to validate page title
Cypress.Commands.add('validateTitle', (expectedTitle) => {
    cy.title().should('eq', expectedTitle);
});

// Custom command to check if "Keynote Addresses" is present and correct
Cypress.Commands.add('verifyKeynoteSection', (expectedText) => {
    cy.contains(expectedText)
        .should('be.visible')
        .and('have.text', expectedText);
});
