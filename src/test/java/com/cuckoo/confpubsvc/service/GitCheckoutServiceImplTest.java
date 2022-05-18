package com.cuckoo.confpubsvc.service;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GitCheckoutServiceImplTest {

	@Autowired
	GitCheckoutServiceImpl gitCheckoutServiceImpl;
	
	@Test
	void test() throws InvalidRemoteException, TransportException, IOException, GitAPIException {
		gitCheckoutServiceImpl.cloneRepository();
	}

}
