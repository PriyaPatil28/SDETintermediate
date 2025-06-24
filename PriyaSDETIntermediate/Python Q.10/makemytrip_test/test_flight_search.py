import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.keys import Keys
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time


@pytest.fixture
def setup_browser():
    options = webdriver.ChromeOptions()
    options.add_argument("--start-maximized")
    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
    yield driver
    driver.quit()


def test_round_trip_flight_search(setup_browser):
    driver = setup_browser
    wait = WebDriverWait(driver, 20)

    # Step 1: Launch MakeMyTrip
    driver.get("https://www.makemytrip.com/")
    time.sleep(3)  # Let the page load and dismiss pop-ups if any

    # Click on the body to close login modal
    try:
        driver.find_element(By.TAG_NAME, "body").click()
    except:
        pass

    # Step 2: Click on Flights
    flights_tab = wait.until(EC.element_to_be_clickable((By.XPATH, "//span[text()='Flights']")))
    flights_tab.click()

    # Step 3: Select Round Trip
    round_trip = wait.until(EC.element_to_be_clickable((By.XPATH, "//li[@data-cy='roundTrip']")))
    round_trip.click()

    # Step 4: Enter FROM city - HYD
    from_input = wait.until(EC.element_to_be_clickable((By.ID, "fromCity")))
    from_input.click()
    from_textbox = driver.find_element(By.XPATH, "//input[@placeholder='From']")
    from_textbox.send_keys("HYD")
    wait.until(EC.element_to_be_clickable((By.XPATH, "//p[contains(text(),'Hyderabad, India')]"))).click()

    # Step 5: Enter TO city - MAA
    to_textbox = driver.find_element(By.XPATH, "//input[@placeholder='To']")
    to_textbox.send_keys("MAA")
    wait.until(EC.element_to_be_clickable((By.XPATH, "//p[contains(text(),'Chennai, India')]"))).click()

    # Step 6: Select Departure and Return Dates
    wait.until(EC.element_to_be_clickable((By.XPATH, "//label[@for='departure']"))).click()
    # Select any available departure and return dates
    wait.until(EC.element_to_be_clickable((By.XPATH, "//div[@aria-label and not(contains(@aria-label, 'disabled'))]"))).click()
    time.sleep(1)
    wait.until(EC.element_to_be_clickable((By.XPATH, "(//div[@aria-label and not(contains(@aria-label, 'disabled'))])[14]"))).click()

    # Step 7: Click Search
    search_btn = driver.find_element(By.XPATH, "//a[text()='Search']")
    search_btn.click()

    # Step 8: Verify search results
    wait.until(EC.presence_of_element_located((By.XPATH, "//div[contains(text(),'Flights from')]")))
    assert "flight" in driver.title.lower() or "flights" in driver.page_source.lower(), "Search page not displayed correctly"
