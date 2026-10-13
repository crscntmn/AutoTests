# AutoTests — Java Selenium Automation Framework

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.48.0-green)
![JUnit](https://img.shields.io/badge/JUnit-5.10.2-blue)
![Maven](https://img.shields.io/badge/Maven-build-red)
![REST Assured](https://img.shields.io/badge/REST%20Assured-5.5.6-brightgreen)
![Docker](https://img.shields.io/badge/Docker-supported-blue)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI%2FCD-black)
![Allure](https://img.shields.io/badge/Allure-reporting-ff69b4)

A Java-based test automation framework demonstrating UI and API testing, Page Object Model, test categorization, Docker-based execution, CI/CD with GitHub Actions, and Allure reporting.

The project uses [Demo Web Shop](https://demowebshop.tricentis.com/) as the main UI testing application and [JSONPlaceholder](https://jsonplaceholder.typicode.com/) for API testing.

---

## About the Project

The project demonstrates a maintainable automated testing framework for web applications.

The framework includes:

- UI test automation with Selenium WebDriver
- REST API testing with REST Assured
- Page Object Model
- Reusable page and component classes
- Explicit waits
- Positive and negative test scenarios
- Boundary value testing
- Dynamic test data generation
- JUnit 5 assertions and test tags
- Smoke and Regression test suites
- Docker-based Selenium execution
- GitHub Actions CI/CD
- Cross-platform CI execution
- Java 17 and Java 21 CI matrix
- Allure test reporting
- GitHub Pages publication of test reports

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17+ | Programming language |
| Selenium WebDriver 4.48.0 | UI test automation |
| JUnit 5.10.2 | Test framework |
| Maven | Build and dependency management |
| WebDriverManager 6.1.0 | WebDriver management |
| REST Assured 5.5.6 | REST API testing |
| Allure 2.29.1 | Test reporting |
| Docker | Containerized test execution |
| Docker Compose | Selenium environment orchestration |
| GitHub Actions | CI/CD |
| GitHub Pages | Test report hosting |

---

## Project Architecture

The project uses the **Page Object Model (POM)** approach to separate test scenarios from UI interaction logic.

```text
src/test/java/auto/tests/
│
├── api/
│   ├── ApiConfig.java
│   ├── ApiTest.java
│   ├── model/
│   │   ├── Post.java
│   │   └── User.java
│   └── testdata/
│       └── ApiTestData.java
│
├── base/
│   └── BaseTest.java
│
├── cart/
│   └── CartTest.java
│
├── components/
│   └── HeaderComponent.java
│
├── config/
│   └── Config.java
│
├── login/
│   └── LoginTest.java
│
├── pages/
│   ├── BasePage.java
│   ├── CartPage.java
│   ├── CheckoutPage.java
│   ├── LoginPage.java
│   └── RegistrationPage.java
│
├── registration/
│   └── RegistrationTest.java
│
└── testdata/
    └── TestData.java
```

### Main architectural layers

```text
Test Classes
     │
     ▼
Page Objects / Components
     │
     ▼
BasePage
     │
     ▼
Selenium WebDriver
```

API tests use a separate structure:

```text
ApiTest
   │
   ├── ApiConfig
   ├── POJO Models
   └── API Test Data
        │
        ▼
   REST Assured
```

---

## Test Coverage

The project currently contains **44 active automated tests** covering UI and API functionality.

### UI Testing

The UI tests cover the following areas:

- User registration
- User authentication
- Password recovery
- Shopping cart
- Product addition
- Product quantities
- Cart calculations
- Checkout restrictions
- Validation errors
- Boundary value scenarios

### API Testing

API tests cover:

- GET requests
- POST requests
- PUT requests
- PATCH requests
- DELETE requests
- Path parameters
- Query parameters
- Response headers
- HTTP status codes
- JSON response validation
- POJO serialization/deserialization
- Positive and negative scenarios

---

## Registration Tests

The registration suite contains positive, negative, and boundary-value scenarios.

Examples:

- Successful registration
- Registration with empty fields
- Password mismatch
- Invalid email
- Missing first name
- Missing last name
- Missing email
- Password with minimum allowed length
- Password below minimum length
- Email containing spaces
- Email using uppercase characters

Example boundary-value scenario:

```text
Password length = 6
Expected: registration succeeds
```

```text
Password length = 5
Expected: validation error
```

---

## Login Tests

The authentication suite covers:

- Successful login
- Empty login form
- Incorrect password
- Incorrect email
- Empty email
- Empty password
- Password recovery with an invalid email
- Password recovery with an existing email
- Invalid email with empty password
- Logout

---

## Shopping Cart Tests

The shopping cart suite covers:

- Adding a product to the cart
- Checkout without accepting Terms of Service
- Checkout after accepting Terms of Service
- Adding configurable products
- Adding multiple products
- Adding the same product multiple times
- Changing product quantity
- Checking the cart item counter
- Calculating product subtotal

The framework also uses `BigDecimal` for price calculations:

```java
BigDecimal expectedSubtotal =
        unitPrice.multiply(BigDecimal.valueOf(quantity));
```

This allows the test to validate the calculated subtotal against the value displayed by the application.

---

## API Testing

API automation is implemented using **REST Assured**.

The tests use:

```text
https://jsonplaceholder.typicode.com
```

### Covered HTTP methods

```text
GET
POST
PUT
PATCH
DELETE
```

### Examples

The framework validates:

- HTTP response status codes
- Response body fields
- JSON structure
- Response headers
- Query parameters
- Path parameters
- Response deserialization into Java objects

For example, API responses can be mapped to POJO models:

```java
User responseUser = response.as(User.class);
```

This allows API assertions to be written against strongly typed Java objects rather than raw JSON strings.

---

## Page Object Model

The framework follows the Page Object Model pattern.

Common Selenium operations are centralized in `BasePage`.

Examples of reusable methods:

```java
click()
type()
getText()
getAttribute()
findAll()
isDisplayed()
isSelected()
waitVisible()
waitInvisible()
waitClickable()
```

Page-specific classes contain locators and business-level UI actions.

For example:

```text
RegistrationPage
LoginPage
CartPage
CheckoutPage
```

This approach helps:

- reduce duplicated Selenium code;
- keep locators outside test classes;
- improve test readability;
- simplify maintenance;
- separate test logic from UI implementation.

---

## Reusable Components

Reusable UI components are separated from individual pages.

For example:

```text
HeaderComponent
```

contains common actions such as:

- opening registration;
- opening login;
- opening the shopping cart;
- logging out;
- adding products to the cart;
- reading the cart item counter.

This allows multiple test classes to reuse the same functionality.

---

## Explicit Waits

The framework uses Selenium's `WebDriverWait` and `ExpectedConditions` instead of relying exclusively on fixed delays.

Examples:

```java
wait.until(ExpectedConditions.elementToBeClickable(locator));
```

```java
wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
```

```java
wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
```

The default explicit wait timeout is **10 seconds**.

---

## Test Suites

Tests are categorized using JUnit 5 tags.

### Smoke

Smoke tests cover critical positive functionality.

Run the Smoke suite with:

```bash
mvn clean test -Dgroups=smoke
```

### Regression

Regression tests cover additional scenarios including negative and validation cases.

Run the Regression suite with:

```bash
mvn clean test -Dgroups=regression
```

### Full Test Suite

Run all tests with:

```bash
mvn clean test
```

---

## Test Distribution

The current active test suite contains:

| Area | Tests |
|---|---:|
| Registration | 11 |
| Login | 10 |
| Shopping Cart | 10 |
| API | 13 |
| **Total** | **44** |

The project also contains additional commented-out cart scenarios that can be re-enabled and stabilized as the framework evolves.

---

## Docker

The project supports running Selenium tests in Docker.

Docker Compose is used to orchestrate:

```text
┌───────────────────────┐
│     Docker Compose    │
│                       │
│  ┌─────────────────┐  │
│  │ Selenium Chrome │  │
│  │    :4444        │  │
│  └────────┬────────┘  │
│           │           │
│           │ WebDriver │
│           ▼           │
│  ┌─────────────────┐  │
│  │    AutoTests    │  │
│  │      Maven      │  │
│  └─────────────────┘  │
│                       │
└───────────────────────┘
```

The Selenium container uses:

```text
selenium/standalone-chrome:latest
```

The test container connects to Selenium using:

```text
SELENIUM_REMOTE_URL=http://selenium:4444/wd/hub
```

### Run Docker tests

Start the environment with:

```bash
docker compose up --build
```

The tests are executed automatically after the Selenium container becomes healthy.

### Run a specific test suite

Smoke:

```bash
TEST_GROUP=smoke docker compose up --build
```

Regression:

```bash
TEST_GROUP=regression docker compose up --build
```

Full suite:

```bash
docker compose up --build
```

---

## CI/CD

The project uses **GitHub Actions** for automated test execution.

The CI pipeline is orchestrated using reusable workflows.

```text
                    CI Orchestrator
                           │
             ┌─────────────┴─────────────┐
             │                           │
             ▼                           ▼
       Selenium CI                 Docker Selenium CI
             │                           │
             ▼                           ▼
         Test Matrix                  Docker Tests
             │                           │
             └─────────────┬─────────────┘
                           ▼
                    Allure Reports
                           │
                           ▼
                     GitHub Pages
```

---

## CI Orchestrator

The main workflow coordinates:

- Selenium CI
- Docker Selenium CI
- CI result validation
- Allure report publication

The workflow can be triggered by:

- push to `main`;
- push to `develop`;
- pull request to `main`;
- scheduled execution;
- manual execution.

---

## Selenium CI Matrix

The Selenium CI workflow runs tests across multiple operating systems and Java versions.

Current matrix:

| Operating System | Java | Browser |
|---|---:|---|
| Ubuntu | 17 | Chrome |
| Ubuntu | 21 | Chrome |
| Windows | 17 | Chrome |
| Windows | 21 | Chrome |
| macOS | 17 | Chrome |
| macOS | 21 | Chrome |

This allows the same automated test suite to be validated across different environments.

---

## Manual CI Execution

The workflow can be started manually using GitHub Actions.

Available suites:

```text
all
smoke
regression
```

This makes it possible to execute only the required test group without changing the project code.

---

## Allure Reporting

The project uses **Allure** for test reporting.

Allure results are generated after test execution and uploaded as GitHub Actions artifacts.

The CI pipeline then publishes the generated reports to GitHub Pages.

### Live Allure Reports

**[Open Allure Test Reports](https://crscntmn.github.io/AutoTests/)**

The reports are organized by:

```text
Allure Reports
│
├── Selenium CI
│   ├── Ubuntu
│   │   ├── Java 17
│   │   │   └── Chrome
│   │   └── Java 21
│   │       └── Chrome
│   │
│   ├── Windows
│   │   ├── Java 17
│   │   └── Java 21
│   │
│   └── macOS
│       ├── Java 17
│       └── Java 21
│
└── Docker Selenium CI
    └── Chrome
```

The reports are generated automatically by GitHub Actions.

---

## Artifacts

CI workflows store test artifacts including:

- Maven Surefire reports;
- Allure results;
- generated Allure reports.

This makes it possible to investigate failed test runs even when the workflow itself has already finished.

---

## Configuration

Application and test configuration is separated from test classes.

The main UI application URL is stored in:

```text
Config.java
```

Environment variables can be used to provide test credentials and runtime configuration.

Supported variables include:

```text
PASSWORD
SHORT_PASSWORD
INVALIDATE_PASSWORD
WRONG_PASSWORD
BROWSER
SELENIUM_REMOTE_URL
TEST_GROUP
```

For local development, default test values are available where appropriate.

For CI execution, sensitive configuration is provided through GitHub Actions Secrets.

---

## Local Execution

### Requirements

To run the project locally, install:

- Java 17 or higher
- Maven
- Google Chrome
- Git

Docker is required only for containerized execution.

---

### Clone the repository

```bash
git clone https://github.com/crscntmn/AutoTests.git
cd AutoTests
```

---

### Run all tests

```bash
mvn clean test
```

---

### Run Smoke tests

```bash
mvn clean test -Dgroups=smoke
```

---

### Run Regression tests

```bash
mvn clean test -Dgroups=regression
```

---

### Generate Allure report locally

After test execution:

```bash
mvn allure:serve
```

The command generates and opens the Allure report locally.

---

## Project Structure

```text
AutoTests/
│
├── .github/
│   └── workflows/
│       ├── ci-orchestrator.yml
│       ├── ci.yml
│       ├── docker-ci.yml
│       └── allure-pages.yml
│
├── src/
│   └── test/
│       └── java/
│           └── auto/
│               └── tests/
│                   ├── api/
│                   ├── base/
│                   ├── cart/
│                   ├── components/
│                   ├── config/
│                   ├── login/
│                   ├── pages/
│                   ├── registration/
│                   └── testdata/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .gitignore
└── README.md
```

---

## Framework Design Principles

The project follows several principles intended to keep the test suite maintainable:

- Page Object Model
- Reusable page/component methods
- Separation of test logic and UI interaction
- Explicit waits instead of unnecessary fixed delays
- Centralized test data
- Environment-based configuration
- Test categorization with JUnit tags
- Reusable GitHub Actions workflows
- Containerized test execution
- Automated reporting

---

## Known Limitations

This repository is a demonstration automation framework built around publicly available test applications.

The UI tests depend on the current behavior and availability of:

```text
https://demowebshop.tricentis.com/
```

API tests depend on:

```text
https://jsonplaceholder.typicode.com/
```

Browser and WebDriver behavior may vary depending on the browser version, operating system, and CI environment.

The Docker CI configuration currently focuses on Selenium Standalone Chrome.

---

## Project Goals

The main goals of the project are to demonstrate practical skills in:

- UI automation;
- API automation;
- Java test development;
- Selenium WebDriver;
- JUnit 5;
- Page Object Model;
- test data management;
- test suite organization;
- Docker;
- CI/CD;
- GitHub Actions;
- Allure reporting;
- cross-platform automated testing.

---

## Author

**Semyon**

QA Engineer focused on:

- Manual Testing
- UI Test Automation
- Java
- Selenium WebDriver
- REST API Testing
- JUnit 5
- Docker
- CI/CD
- GitHub Actions
- Allure

---

## Links

- **Repository:** https://github.com/crscntmn/AutoTests
- **Allure Reports:** https://crscntmn.github.io/AutoTests/
- **Demo Web Shop:** https://demowebshop.tricentis.com/
- **JSONPlaceholder:** https://jsonplaceholder.typicode.com/

---

## Project Status

The framework is actively developed and can be extended with additional:

- UI test scenarios;
- API test scenarios;
- Page Objects;
- reusable components;
- browser configurations;
- CI/CD scenarios;
- test reporting features.
