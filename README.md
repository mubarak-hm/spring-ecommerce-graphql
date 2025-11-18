
# Spring GraphQL E-Commerce API

This project provides a structured, high-performance GraphQL API for an e-commerce domain using Spring Boot 3 and Java 21. The design focuses on predictable query behavior, controlled data loading, and eliminating the N+1 query pattern through explicit batching.

## Architecture Overview

<img width="1839" height="1012" alt="image" src="https://github.com/user-attachments/assets/bd384284-f29a-40a3-b4fa-b23531e6c100" />

### N+1 Query Mitigation

The implementation avoids implicit lazy-loading and uncontrolled resolver execution by relying on explicit batch resolvers (`@BatchMapping`) for all relational fields.

Key characteristics:

- **Batch loaders for all associations** (products → reviews, orders → items, items → product, product → category, etc.).
- **Foreign-key accessors** (e.g., `getCategoryId()`) to prevent Hibernate from triggering lazy proxies.
- **Aggregated computed fields** (e.g., `averageRating`) resolved via dedicated SQL aggregation queries, not in-memory iteration.

This ensures each level of the graph executes in a fixed, minimal number of SQL operations.

### Layered Structure

- **GraphQL Controllers**  
  Responsible for mapping schema fields to service calls, including batch resolvers.
  
- **Services**  
  Contain domain logic and orchestrate batch operations. Completely schema-agnostic.

- **Repositories**  
  Use explicit `IN`-clause queries, projections, and controlled fetch behavior suitable for batching.

### Schema-First Contract

A schema-first workflow defines all API structures.  
Mutations use dedicated `Input` types rather than exposing JPA entities, keeping persistence concerns isolated from the API contract.



## Query Execution Model

Field resolution follows a deterministic two-stage model:

1. **Root resolvers** (`@QueryMapping`) fetch top-level entities in a single query.
2. **Batch resolvers** (`@BatchMapping`) gather parent IDs and fetch children in one operation per relationship.

Example: querying orders with nested items, products, and categories executes exactly four SQL queries—regardless of list size.

```graphql
query {
  orders {
    id
    items {
      quantity
      product {
        name
        category {
          name
        }
      }
    }
  }
}
````

## Getting Started

### Requirements

* Java 21
* Maven
* Docker / Docker Compose

### Setup

Clone the repository:

```bash
git clone https://github.com/yourusername/spring-ecommerce-graphql.git
cd spring-ecommerce-graphql
```

Start PostgreSQL:

```bash
docker-compose up -d
```

Build:

```bash
./mvnw clean install
```

Run:

```bash
./mvnw spring-boot:run
```

GraphiQL available at:
`http://localhost:8080/graphiql?path=/graphql`

## Example Operations

### Mutation

```graphql
mutation {
  createOrder(input: {
    paymentMethod: PAYPAL
    items: [
      { productId: "1", quantity: 1 },
      { productId: "3", quantity: 2 }
    ]
  }) {
    id
    total
    status
  }
}
```

### Query

```graphql
query {
  products {
    name
    price
    averageRating
    category {
      name
    }
    seller {
      name
      reviews {
        rating
      }
    }
  }
}
```


