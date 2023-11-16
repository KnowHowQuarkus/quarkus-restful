# RESTful Library

### Paging

REST endpoints that return all entities, such as `GET /users`, SHOULD support paging. Java methods implementing pageable endpoints SHOULD be annotated with `@PageableResource`. If used, Integer query params `page` and `size` MUST be defined.

This library will automatically validate those parameters and will reject requests with a 400 Bad Request where the client is attempting to fetch more than 100 entities in a single query. If the Java method does not have `@QueryParam("page")` or `@QueryParam("size")` then a 500 Internal Server Error will be generated to indicate a programming bug.

```java
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    @Pageable
    List<Users> getUsers(
        @QueryParam("page") @Min(0) @DefaultValue("0") Integer page,
        @QueryParam("size") @Min(0) @DefaultValue("50") Integer size) {
    }

```

### Exceptions / error responses

Applications MAY make use of exceptions in `dev.knowhowto.quarkus.restful.exception`. These are automatically turned into HTTP responses as follows:

| Exception                      | HTTP response code | Situation                                                             |
|--------------------------------|--------------------|-----------------------------------------------------------------------|
| `ConflictException`            | 409                | Another client has performed a conflicting operation on the entity.   |
| `UnprocessableEntityException` | 422                | The operation cannot be performed on the entity in its current state. |


### Versioning

Project uses a three-segment [CalVer](https://calver.org/) scheme, with a short year in the major version slot, short month in the minor version slot, and micro/patch version in the third
and final slot.

```
YY.MM.MICRO
```

1. **YY** - short year - 6, 16, 106
1. **MM** - short month - 1, 2 ... 11, 12
1. **MICRO** -  "patch" segment
