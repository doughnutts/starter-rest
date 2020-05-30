package me.jiaklor.starter.rest.annotations.oas.http;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation
@RequestMapping
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpOperation {
    @AliasFor(annotation = RequestMapping.class)
    String name() default "";

    @AliasFor(annotation = RequestMapping.class, value = "path")
    String[] value() default {};

    @AliasFor(annotation = RequestMapping.class, value = "value")
    String[] path() default {};

    @AliasFor(annotation = RequestMapping.class)
    RequestMethod[] method() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] params() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] headers() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] consumes() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] produces() default {};

    @AliasFor(annotation = Operation.class, value = "method")
    String opMethod() default "";

    @AliasFor(annotation = Operation.class)
    String[] tags() default {};

    @AliasFor(annotation = Operation.class)
    String summary() default "";

    @AliasFor(annotation = Operation.class)
    String description() default "";

    @AliasFor(annotation = Operation.class)
    RequestBody requestBody() default @RequestBody;

    @AliasFor(annotation = Operation.class)
    ExternalDocumentation externalDocs() default @ExternalDocumentation;

    @AliasFor(annotation = Operation.class)
    String operationId() default "";

    @AliasFor(annotation = Operation.class)
    Parameter[] parameters() default {};

    @AliasFor(annotation = Operation.class)
    ApiResponse[] responses() default {};

    @AliasFor(annotation = Operation.class)
    boolean deprecated() default false;

    @AliasFor(annotation = Operation.class)
    SecurityRequirement[] security() default {};

    @AliasFor(annotation = Operation.class)
    Server[] servers() default {};

    @AliasFor(annotation = Operation.class)
    Extension[] extensions() default {};

    @AliasFor(annotation = Operation.class)
    boolean hidden() default false;

    @AliasFor(annotation = Operation.class)
    boolean ignoreJsonView() default false;
}
