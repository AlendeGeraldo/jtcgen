var templates = {
	mockDef: '{{shortClazz}} {{shortClazzLower}} = mock({{shortClazz}}.class);' +
		  '\n\t\twhen({{shortClazzLower}}.{{method}}).thenReturn({{returns}});',
	mockDefSameCall: 'when({{shortClazzLower}}.{{method}}).thenReturn({{returns}});',	  
	setup: '{{shortClazz}} {{shortClazzLower}} = new {{shortClazz}}({{params}});',
	assert: '{{returns}} expected{{expectedCount}} = {{shortClazzLower}}.{{method}}({{params}});' +
			'\n\t\t' + '{{assert}}({{expected}}, expected{{expectedCount}}{{paramAdd}});',
	assertOneParam: '{{returns}} expected{{expectedCount}} = {{shortClazzLower}}.{{method}}({{params}});' +
			'\n\t\t' + '{{assert}}(expected{{expectedCount}}{{paramAdd}});',
	assertVoid: '{{shortClazzLower}}.{{method}}({{params}});' +
			+ '{{returns}} expected{{expectedCount}} = {{shortClazzLower}}.{{methodTarget}};' +
			'\n\t\t' + '{{assert}}({{expected}}, expected{{expectedCount}}{{paramAdd}});'
			
} 