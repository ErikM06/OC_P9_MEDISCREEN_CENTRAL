package com.mediscreen.central.controller;

/*
@ExtendWith(SpringExtension.class)
@WebMvcTest(DiabetesRiskController.class)

public class PatientControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    PatientDiabetesRiskProxy patientDiabetesRiskProxy;

    @MockBean
    PatientClientProxy patientClientProxy;

    @MockBean
    PatientNotesClientProxy patientNotesClientProxy;


    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void getPatientTest () throws Exception {

        mockMvc.perform(get("/central/get-patient-notes-list/")
                        .param("patId",asJsonString(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addPatient() throws Exception {
        Patient patient =new Patient("firstNameTest", "lastNameTest", new Date(System.currentTimeMillis()),
                "F", "addressTest", "phoneTest", null);

        given(patientClientProxy.addPatient(any(Patient.class))).willReturn(patient);

        mockMvc.perform(post("/central/validate-update/")
                        .param("id", asJsonString(patient.getId()))
                        .content(asJsonString(patient))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePatientNote() throws Exception {
        Patient patient =new Patient("firstNameTest", "lastNameTest", new Date(System.currentTimeMillis()),
                "F", "addressTest", "phoneTest", null);


        given(patientClientProxy.addPatient(any(Patient.class))).willReturn(patient);

        mockMvc.perform(post("/central/validate-update/")
                        .param("id", asJsonString(patient.getId()))
                        .content(asJsonString(patient))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePatientById() throws Exception {
        mockMvc.perform(get("/central/delete-patient-by-id/")
                        .param("id", asJsonString(1L)))
                .andExpect(status().isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}*/

