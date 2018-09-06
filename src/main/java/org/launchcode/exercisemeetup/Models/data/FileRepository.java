package org.launchcode.exercisemeetup.Models.data;


import org.launchcode.exercisemeetup.Models.User;
import org.launchcode.exercisemeetup.Models.forms.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public interface FileRepository extends JpaRepository<FileModel, Long> {
    public FileModel findByName(String name);

    public ArrayList<FileModel> findByUser(User user);
}
