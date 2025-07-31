package edu.neu.csye7374.pattern.strategy;

import edu.neu.csye7374.model.StaffMember;
import edu.neu.csye7374.repository.StaffMemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StaffMemberStrategy implements OperationStrategyAPI {

    private StaffMemberRepository staffMemberRepo;
    private int id;
    private StaffMember staffMember;

    public StaffMemberStrategy(StaffMemberRepository staffMemberRepo, StaffMember staffMember) {
        this.staffMemberRepo = staffMemberRepo;
        this.staffMember = staffMember;
    }

    public StaffMemberStrategy(StaffMemberRepository staffMemberRepo, int id) {
        this.staffMemberRepo = staffMemberRepo;
        this.id = id;
    }

    @Override
    public void add() {
//        if(staffMemberRepo.usernameExists(staffMember.getUsername()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
//        staffMemberRepo.savestaffMember(staffMember);
    }

    @Override
    public void update() {
//        staffMemberRepo.updatestaffMember(staffMember);
    }

    @Override
    public void delete() {
//        staffMember emp = staffMemberRepo.getstaffMemberbyID(id);
//        staffMemberRepo.deletestaffMember(emp);
    }

}