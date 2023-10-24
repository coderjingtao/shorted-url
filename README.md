# Short URL Generator

The Short URL Generator is a Java-based Spring Boot project that provides a service for generating short URLs for long URLs. This project is designed for sharing long URLs more efficiently, especially on social networks.

## Table of Contents
- [Key Technical Points](#key-technical-points)
- [Usage](#usage)
- [Configuration](#configuration)
- [Dependencies](#dependencies)
- [Contributing](#contributing)

## Key Technical Points

The Short URL Generator project incorporates several key technical points to achieve its functionality:

### MurmurHash

MurmurHash is used for generating short, unique identifiers from long URLs. It is a non-cryptographic hash function that provides good distribution and is suitable for generating unique short URLs.

### Base62 Encoding

Base62 encoding is employed to convert the unique identifiers generated by MurmurHash into short URL strings. It uses a character set of A-Z, a-z, and 0-9, resulting in shorter and more user-friendly short URLs.

### Bloom Filter

A Bloom Filter is used for efficient checking of the existence of a given short URL in the system. It is a probabilistic data structure that allows for quick membership queries, reducing the need to access the database for every lookup.

### Redis

Redis is used as the backend data store to map short URLs to their corresponding long URLs. It provides fast data retrieval and caching, making the redirection process efficient.


## Usage

To use the Short URL Generator in your project, follow these steps:

1. Clone this repository.
2. Configure your `application.properties` with the appropriate settings for your environment.
3. Build and run the Spring Boot application.
4. Access http://localhost:8080 or use the provided API endpoints to generate and manage short URLs.

## Configuration

You can configure the project by modifying the `application.properties` file. The configuration includes settings for database connections, Redis, and other application-specific properties.

## Dependencies

The project relies on the following key dependencies:

- Spring Boot: A framework for building Java applications.
- Redis: An in-memory data store used for caching short URL mappings.
- MurmurHash: A hash function for generating unique identifiers.
- Base62: A library for encoding and decoding data in Base62.
- Bloom Filter: A data structure for efficient membership queries.

## Contributing

If you'd like to contribute to this project, please follow the standard GitHub fork and pull request workflow. We welcome contributions, bug reports, and feature requests.