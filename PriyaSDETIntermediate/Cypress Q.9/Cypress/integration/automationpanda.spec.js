describe('Automation Panda Website Test Suite', () => {
    let testData;

    before(() => {
        // Load fixture data before tests
        cy.fixture('testData').then((data) => {
            testData = data;
        });
    });

    beforeEach(() => {
        // Visit the base URL before each test
        cy.visit(testData.baseUrl);
    });

    it('a. Should verify the homepage title', () => {
        cy.title().should('eq', testData.homeTitle);
    });

    it('b. Should click on Speaking and verify the page title', () => {
        cy.contains('Speaking').click();
        cy.title().should('eq', testData.speakingTitle);
    });

    it('c. Should verify "Keynote Addresses" is present on the Speaking page', () => {
        cy.contains('Speaking').click();
        cy.get('h2, h3, h4, p') // check multiple types in case layout changes
            .contains(testData.keynoteText)
            .should('be.visible')
            .and('have.text', testData.keynoteText);
    });
});
