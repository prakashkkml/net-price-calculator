
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
- Swagger-UI
- Documentation
## Supported Operations

#### Get calculated net price

```http
  GET /api/v1/net-price-calculator
```
| Request Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `grossPrice`      | `BigDecimal` | **Required**. To specify the gross price|
| `countryISO`| `string` | **Required**. To countryISO-Code (eg: DE, US etc.)|


#### Get VAT rate for the country

```http
  GET /api/v1/tax-rate-provider/{countryCode}
```
| Path Variable | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `countryCode`      | `String` | **Required**. A valid countryISO-Code (eg: DE, US etc.)|


#### Update VAT rate for the country

```http
  PUT /api/v1/tax-rate-provider
```
| Request Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `countryCode`      | `String` | **Required**. A valid countryISO-Code (eg: DE, US etc.)|
| `vat`      | `BigDecimal` | **Required**. VAT rate of the country|


## Demo Live
https://net-price-calculator.herokuapp.com/swagger-ui/index.html