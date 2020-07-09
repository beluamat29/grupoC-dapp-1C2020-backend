package com.example.demo.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import javax.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.example.demo")
public class ArchitectureTest {

    @ArchTest
    private final ArchRule persistableEntitiesMustBeInDominio =
            classes().that().areAnnotatedWith(Entity.class)
                    .should().resideInAPackage("com.example.demo.model.*")
                    .because("Persistable entities should never be outside model folder");

    @ArchTest
    private final ArchRule serviceNamingConvention =
            classes().that().resideInAPackage("com.example.demo.services.*")
                    .should().haveSimpleNameEndingWith("Service");
}
