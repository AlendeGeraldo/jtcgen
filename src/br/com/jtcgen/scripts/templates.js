var templates = {
	mockDef: '{{shortClazz}} {{shortClazzLower}} = mock({{shortClazz}}.class);' +
		  '\n\t\twhen({{shortClazzLower}}.{{method}}).thenReturn({{returns}});',
	setup: '{{shortClazz}} {{shortClazzLower}} = new {{shortClazz}}({{params}});',
	assert: '{{returns}} expected = {{shortClazzLower}}.{{method}}({{params}});' +
			'\n\t\t' + '{{assert}}({{expected}}, expected{{paramAdd}});'
}