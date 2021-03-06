package dtu.library.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import cucumber.api.java.en.*;
import dtu.library.app.*;

public class CreateProject {

	private Project project;
	private ArrayList <Worker> workers = new ArrayList<Worker>();
	private String errmsg;

	public CreateProject() {
	}

	@Given("^that a worker is logged in$")
	public void thatAWorkerIsLoggedIn() throws Exception {
		workers.add(new Worker("abcd"));
	}

	@When("^I create project with name \"([^\"]*)\"$")
	public void iCreateProjectWithName(String name) throws Exception {
		project = new Project(name);
	}

	@Then("^the projekt with name \"([^\"]*)\" and serial number \"([^\"]*)\" is created$")
	public void theProjectWithNameAndSerialNumberIsCreated(String name, String iD) {
		assertTrue(project.getName().equals(name) && project.getID().equals(iD));
	}

	@When("^I set worker with ID \"([^\"]*)\" as project leader$")
	public void iSetWorkerWithIDAsProjectLeader(String iD) throws Exception {
		Worker Brian = new Worker (iD);
		project.setProjectLeader(Brian, Brian);
	}

	@Then("^worker with ID \"([^\"]*)\" is project leader on project \"([^\"]*)\"$")
	public void workerWithIDIsProjectLeaderOnProject(String iD, String name) throws Exception {
		assertTrue(project.getProjectLeader().getID().equals(iD));
	}

	@When("^worker \"([^\"]*)\" sets worker \"([^\"]*)\" as project leader$")
	public void workerSetsWorkerAsProjectLeader(String iD1, String iD2) throws Exception {
		try {
			Worker Brian = new Worker(iD1);
			Worker Rasmus = new Worker(iD2);
			project.setProjectLeader(Brian, Rasmus);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}
	}

	@Then("^I get the project leader change error \"([^\"]*)\"$")
	public void iGetTheProjectLeaderChangeError(String error) throws Exception {
		assertTrue(errmsg.equals(error));
	}

	@When("^I add worker with ID \"([^\"]*)\" to project with name \"([^\"]*)\"$")
	public void iAddWorkerWithIDToProjectWithName(String iD, String name) throws Exception {
		try {
			Worker Rasmus = new Worker(iD);
			project.addWorker(Rasmus);
		} catch (Exception e1) {
			errmsg = e1.getMessage();
		}
	}

	@Then("^worker \"([^\"]*)\" is working on project \"([^\"]*)\"$")
	public void workerIsWorkingOnProject(String iD, String name) throws Exception {
		assertTrue(project.getWorkers().get(0).getID().equals(iD));
	}

	@Then("^I get the allready working on that error \"([^\"]*)\"$")
	public void iGetTheAllreadyWorkingOnThatError(String error) throws Exception {
		assertTrue(errmsg.equals(error));
	}

	@Given("^that the worker with ID \"([^\"]*)\" logs in$")
	public void thatTheWorkerWithIDLogsIn(String name) throws Exception {
		workers.add(new Worker(name));
	}
	
	@Given("^that the project leader \"([^\"]*)\" logs in$")
	public void thatTheProjectLeaderLogsIn(String name) throws Exception {
		workers.add(new Worker(name));
	}
	
	@And("^the project leader with ID \"([^\"]*)\" and the workers with ID \"([^\"]*)\" and ID \"([^\"]*)\" works on project with name \"([^\"]*)\"$")
	public void thatTheWorkersWithIDWorksOnProject(String bria, String liam, String rasm, String proj) throws Exception {
		project = new Project(proj);
		Worker Brian = new Worker(bria);
		project.setProjectLeader(Brian, Brian);
		Worker Liam = new Worker(liam);
		Worker Rasmus = new Worker(rasm);
		project.addWorker(Liam);
		project.addWorker(Rasmus);
	}

	@When("^The worker with ID \"([^\"]*)\" removes the worker with ID \"([^\"]*)\"$")
	public void IRemoveWorker(String remover, String removewho) throws Exception {
		try {
			Worker removeworker = null;
			for(int i = 0; i < project.getWorkers().size();i++) {
				if(project.getWorkers().get(i).getID().equals(remover)) {
					removeworker = project.getWorkers().get(i);
				} else if (project.getProjectLeader().getID().equals(remover)) {
					removeworker = project.getProjectLeader();
				}
			}
			if(!(removeworker==null)) {
				project.removeWorker(removeworker, removewho);
			}
		} catch (Exception e2) {
			errmsg = e2.getMessage();
		}
	}

	@Then("^the worker \"([^\"]*)\" is no longer working on project \"([^\"]*)\"$")
	public void theWorkerIsNoLongerWorkingOnProject(String name, String proj) {
		boolean searchresult = true;
		for(int i = 0; i < project.getWorkers().size(); i++) {
			if (project.getWorkers().get(i).getID().equals(name)) {
				searchresult = false;
			}
		}
		assertTrue(searchresult==true);
	}
	
	@Then("^I get the cannot remove as non-project leader error \"([^\"]*)\"$")
	public void IGetTheCannotRemoveAsNonProjectLeaderError(String error) {
		assertTrue(errmsg.equals(error));
	}
	
	@Then("^I get the no worker error \"([^\"]*)\"$")
	public void IGetNoWorkerWithIDError(String error) {
		assertTrue(errmsg.equals(error));
	}
}
