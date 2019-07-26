public class OngoingInterviewScenario extends Scenario {

    private Interviewer interviewer;

    public OngoingInterviewScenario(UserMenu userMenu, Interviewer interviewer){
        super(userMenu, LayoutMode.REGULAR);
        this.interviewer = interviewer;

    }
    public static void main(String[] args) {
        OngoingInterviewScenario = new OngoingInterviewScenario(new UserMenu());
        scenario.init();

    }

    private void init(){
        super.init();
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);

    }
}
