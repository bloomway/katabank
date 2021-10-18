package com.kleematik.katabank;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.kleematik.katabank.domain",
        "com.kleematik.katabank.infra"
})
class KataBankApplicationTest { }