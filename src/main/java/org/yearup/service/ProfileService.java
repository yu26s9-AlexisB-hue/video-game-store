package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    public Profile getProfileByUserId(int userId){
        return profileRepository.findByUserId(userId);
    }

    public Profile updateUserInfo(int userId, Profile profile){
        Profile existingProfile = profileRepository.findByUserId(userId);

        if (existingProfile == null){
            throw new RuntimeException("Profile not found.");
        }
        existingProfile.setFirstName(profile.getFirstName());
        existingProfile.setLastName(profile.getLastName());
        existingProfile.setPhone(profile.getPhone());
        existingProfile.setEmail(profile.getEmail());
        existingProfile.setAddress(profile.getAddress());
        existingProfile.setCity(profile.getCity());
        existingProfile.setState(profile.getState());
        existingProfile.setZip(profile.getZip());

        return profileRepository.save(existingProfile);
    }
}
