package org.gatherdata.app.sling.resource.rosa.internal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import java.util.regex.Matcher;

import org.junit.Test;

public class RosaResourceProviderTest {

	@Test
	public void shouldMatchFormListPathWithoutExtension() {
		final String FORM_LIST_PATH = RosaResourceProvider.PROVIDER_ROOT + "forms";
        Matcher urlMatcher = RosaResourceProvider.FORM_LIST_URL_PATTERN.matcher(FORM_LIST_PATH);
        assertTrue(urlMatcher.matches());
        //assertThat(namespaceMatcher.group(1), is(expectedGroupOne));
	}

	@Test
	public void shouldNotMatchFormListPathWithExtension() {
		final String FORM_LIST_PATH = RosaResourceProvider.PROVIDER_ROOT + "forms.xml";
        Matcher urlMatcher = RosaResourceProvider.FORM_LIST_URL_PATTERN.matcher(FORM_LIST_PATH);
        assertFalse(urlMatcher.matches());
        //assertThat(namespaceMatcher.group(1), is(expectedGroupOne));
	}
	
	@Test
	public void shouldMatchPostDataPathWithoutExtension() {
		final String POST_DATA_PATH = RosaResourceProvider.PROVIDER_ROOT + "data";
        Matcher urlMatcher = RosaResourceProvider.POST_DATA_URL_PATTERN.matcher(POST_DATA_PATH);
        assertTrue(urlMatcher.matches());
        //assertThat(namespaceMatcher.group(1), is(expectedGroupOne));
	}

	@Test
	public void shouldNotMatchPostDataPathWithExtension() {
		final String POST_DATA_PATH = RosaResourceProvider.PROVIDER_ROOT + "data.xml";
        Matcher urlMatcher = RosaResourceProvider.POST_DATA_URL_PATTERN.matcher(POST_DATA_PATH);
        assertFalse(urlMatcher.matches());
        //assertThat(namespaceMatcher.group(1), is(expectedGroupOne));
	}

	@Test
	public void shouldMatchFormTemplatePathWithoutExtension() {
		final String EXPECTED_GROUP_1 = "gather";
		final String EXPECTED_GROUP_2 = "foo";
		final String FORM_TEMPLATE_PATH = RosaResourceProvider.PROVIDER_ROOT + 
			"forms/" + EXPECTED_GROUP_1 + "/" + EXPECTED_GROUP_2;
        Matcher urlMatcher = RosaResourceProvider.FORM_URL_PATTERN.matcher(FORM_TEMPLATE_PATH);
        assertTrue(RosaResourceProvider.FORM_URL_PATTERN.toString() + " should match " + FORM_TEMPLATE_PATH,
        		urlMatcher.matches());
        assertThat(urlMatcher.group(1), is(EXPECTED_GROUP_1));
        assertThat(urlMatcher.group(2), is(EXPECTED_GROUP_2));
	}

	@Test
	public void shouldMatchFormTemplatePathWithDashInGroupOne() {
		final String EXPECTED_GROUP_1 = "gather-it-up";
		final String EXPECTED_GROUP_2 = "foo";
		final String FORM_TEMPLATE_PATH = RosaResourceProvider.PROVIDER_ROOT + 
			"forms/" + EXPECTED_GROUP_1 + "/" + EXPECTED_GROUP_2;
        Matcher urlMatcher = RosaResourceProvider.FORM_URL_PATTERN.matcher(FORM_TEMPLATE_PATH);
        assertTrue(RosaResourceProvider.FORM_URL_PATTERN.toString() + " should match " + FORM_TEMPLATE_PATH,
        		urlMatcher.matches());
        assertThat(urlMatcher.group(1), is(EXPECTED_GROUP_1));
        assertThat(urlMatcher.group(2), is(EXPECTED_GROUP_2));
	}

	@Test
	public void shouldMatchFormTemplatePathWithDashInGroupTwo() {
		final String EXPECTED_GROUP_1 = "gather";
		final String EXPECTED_GROUP_2 = "foo-bar";
		final String FORM_TEMPLATE_PATH = RosaResourceProvider.PROVIDER_ROOT + 
			"forms/" + EXPECTED_GROUP_1 + "/" + EXPECTED_GROUP_2;
        Matcher urlMatcher = RosaResourceProvider.FORM_URL_PATTERN.matcher(FORM_TEMPLATE_PATH);
        assertTrue(RosaResourceProvider.FORM_URL_PATTERN.toString() + " should match " + FORM_TEMPLATE_PATH,
        		urlMatcher.matches());
        assertThat(urlMatcher.group(1), is(EXPECTED_GROUP_1));
        assertThat(urlMatcher.group(2), is(EXPECTED_GROUP_2));
	}

	@Test
	public void shouldMatchFormTemplatePathWithDashInBothGroups() {
		final String EXPECTED_GROUP_1 = "gather-4-eva";
		final String EXPECTED_GROUP_2 = "foo-bar";
		final String FORM_TEMPLATE_PATH = RosaResourceProvider.PROVIDER_ROOT + 
			"forms/" + EXPECTED_GROUP_1 + "/" + EXPECTED_GROUP_2;
        Matcher urlMatcher = RosaResourceProvider.FORM_URL_PATTERN.matcher(FORM_TEMPLATE_PATH);
        assertTrue(RosaResourceProvider.FORM_URL_PATTERN.toString() + " should match " + FORM_TEMPLATE_PATH,
        		urlMatcher.matches());
        assertThat(urlMatcher.group(1), is(EXPECTED_GROUP_1));
        assertThat(urlMatcher.group(2), is(EXPECTED_GROUP_2));
	}

	@Test
	public void shouldMatchFormTemplatePathWithUnderscoreInGroupOne() {
		final String EXPECTED_GROUP_1 = "gather_ed";
		final String EXPECTED_GROUP_2 = "foo";
		final String FORM_TEMPLATE_PATH = RosaResourceProvider.PROVIDER_ROOT + 
			"forms/" + EXPECTED_GROUP_1 + "/" + EXPECTED_GROUP_2;
        Matcher urlMatcher = RosaResourceProvider.FORM_URL_PATTERN.matcher(FORM_TEMPLATE_PATH);
        assertTrue(RosaResourceProvider.FORM_URL_PATTERN.toString() + " should match " + FORM_TEMPLATE_PATH,
        		urlMatcher.matches());
        assertThat(urlMatcher.group(1), is(EXPECTED_GROUP_1));
        assertThat(urlMatcher.group(2), is(EXPECTED_GROUP_2));
	}

	@Test
	public void shouldMatchFormTemplatePathWithUnderscoreInGroupTwo() {
		final String EXPECTED_GROUP_1 = "gather";
		final String EXPECTED_GROUP_2 = "foo_ey";
		final String FORM_TEMPLATE_PATH = RosaResourceProvider.PROVIDER_ROOT + 
			"forms/" + EXPECTED_GROUP_1 + "/" + EXPECTED_GROUP_2;
        Matcher urlMatcher = RosaResourceProvider.FORM_URL_PATTERN.matcher(FORM_TEMPLATE_PATH);
        assertTrue(RosaResourceProvider.FORM_URL_PATTERN.toString() + " should match " + FORM_TEMPLATE_PATH,
        		urlMatcher.matches());
        assertThat(urlMatcher.group(1), is(EXPECTED_GROUP_1));
        assertThat(urlMatcher.group(2), is(EXPECTED_GROUP_2));
	}

	@Test
	public void shouldMatchFormTemplatePathWithUnderscoreInBothGroups() {
		final String EXPECTED_GROUP_1 = "gather_blather";
		final String EXPECTED_GROUP_2 = "foo_ish";
		final String FORM_TEMPLATE_PATH = RosaResourceProvider.PROVIDER_ROOT + 
			"forms/" + EXPECTED_GROUP_1 + "/" + EXPECTED_GROUP_2;
        Matcher urlMatcher = RosaResourceProvider.FORM_URL_PATTERN.matcher(FORM_TEMPLATE_PATH);
        assertTrue(RosaResourceProvider.FORM_URL_PATTERN.toString() + " should match " + FORM_TEMPLATE_PATH,
        		urlMatcher.matches());
        assertThat(urlMatcher.group(1), is(EXPECTED_GROUP_1));
        assertThat(urlMatcher.group(2), is(EXPECTED_GROUP_2));
	}

	@Test
	public void shouldNotMatchFormTemplatePathWithExtraPath() {
		final String EXPECTED_GROUP_1 = "gather";
		final String EXPECTED_GROUP_2 = "foo";
		final String FORM_TEMPLATE_PATH = RosaResourceProvider.PROVIDER_ROOT + 
			"forms/" + EXPECTED_GROUP_1 + "/" + EXPECTED_GROUP_2 + "/extra";
        Matcher urlMatcher = RosaResourceProvider.FORM_URL_PATTERN.matcher(FORM_TEMPLATE_PATH);
        assertFalse(RosaResourceProvider.FORM_URL_PATTERN.toString() + " should not match " + FORM_TEMPLATE_PATH,
        		urlMatcher.matches());
	}
}
