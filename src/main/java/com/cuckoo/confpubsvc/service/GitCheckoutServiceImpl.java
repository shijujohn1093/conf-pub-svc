package com.cuckoo.confpubsvc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.util.FS;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service
public class GitCheckoutServiceImpl implements ScmCheckoutService{


	
	public void cloneRepository() throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		File workingDir = Files.createTempDirectory("workspace").toFile();

		TransportConfigCallback transportConfigCallback = new SshTransportConfigCallback();

		Git git = Git.cloneRepository()
		        .setDirectory(workingDir)
		        .setTransportConfigCallback(transportConfigCallback)
		        .setURI("git@github.com:shijujohn1093/conf-pub-svc.git")
		        .call();
		for(String file : workingDir.list()) {
			System.out.println("------------>"+file);
		}
		
	}
	private static class SshTransportConfigCallback implements TransportConfigCallback {

	    private final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
	        @Override
	        protected void configure(OpenSshConfig.Host hc, Session session) {
	            session.setConfig("StrictHostKeyChecking", "no");
	        }

	        @Override
	        protected JSch createDefaultJSch(FS fs) throws JSchException {
	            JSch jSch = super.createDefaultJSch(fs);
//	            jSch.addIdentity("C:/Users/shiju/.ssh/id_ecdsa",  "pass phrase used".getBytes());	           
	            jSch.addIdentity("<path to ssh>/.ssh/id_ecdsa");

//	            jSch.addIdentity("C:/Users/shiju/.ssh", "".getBytes());
//	            jSch.addIdentity("C:\\workspace", "".getBytes());
	            
	            return jSch;
	        }
	    };

	    @Override
	    public void configure(Transport transport) {
	        SshTransport sshTransport = (SshTransport) transport;
	        sshTransport.setSshSessionFactory(sshSessionFactory);
	    }

	}
}
