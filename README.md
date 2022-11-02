
# A Backend Application for providing Net Price Calculator APIs

A simple backend application where we can calculate the net price from the gross price and country's VAT-rate.

There is an API that solely returns the VAT rate for the country,
and the application has the provision to load all the countries with a configured default-vat rate.
Which can be modified/customized by calling the update **/api/v1/tax-rate-provider**
## Authors

- [@prakashkkml](https://github.com/prakashkkml)
## Tech Stack

**Back End:** Java, Spring Boot, Rest API, Swagger, Junit, Mockito

## Features

- Used LOMBOK to remove boiler plate code
- Implemented **/api/v1/tax-rate-provider** APIs to simulate microservice's synchronous communication behaviour
- Spring Caching
- Live Demo
- Unit & Integration Tests
- Documentation
## Supported Operations

#### Get all repositories

```http
  GET /api/v1/net-price-calculator
```
| Request Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `grossPrice`      | `BigDecimal` | **Required**. To specify the gross price|
| `countryISO`| `string` | **Required**. To countryISO-Code (eg: DE, US etc.)|


## Demo Live



