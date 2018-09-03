package org.launchcode.exercisemeetup.Models.data;


import org.launchcode.exercisemeetup.Models.forms.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FileRepository extends JpaRepository<FileModel, Long> {
    public FileModel findByName(String name);
}
