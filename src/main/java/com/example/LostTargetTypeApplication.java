package com.example;

import com.example.LostTargetTypeApplication.Registrar;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Configuration(proxyBeanMethods = false)
@Import(Registrar.class)
public class LostTargetTypeApplication {

    @Bean
    public SomeBean someBean(Two two) {
        return new SomeBean(two);
    }

    @Bean
    public One one() {
        return new Implementation();
    }

    public static void main(String[] args) {
        SpringApplication.run(LostTargetTypeApplication.class, args);
    }

    static interface One {

    }

    static interface Two {

    }

    static class Implementation implements One, Two {

    }

    static class SomeBean {

        SomeBean(Two two) {

        }

    }

    static class Registrar implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                            BeanDefinitionRegistry registry) {
            RootBeanDefinition definition = new RootBeanDefinition((RootBeanDefinition) registry.getBeanDefinition("one"));
            definition.setTargetType(Two.class);
            registry.registerBeanDefinition("two", definition);
        }

    }

}