package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.domain.Credential;
import com.udacity.jwdnd.course1.cloudstorage.domain.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials(User user) {
        return credentialMapper.getAllCredentials(user.getUserId());
    }

    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(credential);
    }

    public String decryptPassword(Credential credential){
        return this.encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredentialById(credentialId);
    }

    public int addCredential(Credential credential) {
        credential.setKey(this.encryptionService.generateKey());
        credential.setPassword(this.encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credentialMapper.addCredential(credential);
    }
}
