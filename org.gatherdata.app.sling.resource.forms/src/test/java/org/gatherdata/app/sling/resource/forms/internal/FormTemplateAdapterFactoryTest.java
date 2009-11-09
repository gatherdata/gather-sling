package org.gatherdata.app.sling.resource.forms.internal;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;

import java.util.regex.Matcher;

import org.junit.Test;

public class FormTemplateAdapterFactoryTest {

    
    @Test
    public void shouldMatchFormNamespaceUid() {
        final String expectedGroupOne = "gather.org:8080";
        final String expectedGroupTwo = "mock";
        final String expectedGroupThree = "test";
        final String namespaceUid = "http://" + expectedGroupOne + "/" + expectedGroupTwo + "/" + expectedGroupThree;
        
        Matcher namespaceMatcher = FormTemplateAdapterFactory.FORM_NAMESPACE_PATTERN.matcher(namespaceUid);
        
        assertTrue(namespaceMatcher.matches());
        
        assertThat(namespaceMatcher.group(1), is(expectedGroupOne));
        assertThat(namespaceMatcher.group(2), is(expectedGroupTwo));
        assertThat(namespaceMatcher.group(3), is(expectedGroupThree));
        
    }
}
