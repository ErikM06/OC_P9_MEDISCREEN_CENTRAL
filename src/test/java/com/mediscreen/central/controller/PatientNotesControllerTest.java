package com.mediscreen.central.controller;

/*
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PatientNotesControllerTest {
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
        PatientNotes patientNotes = new PatientNotes("idTest", 1L, "contentTest");


        given(patientNotesClientProxy.addPatientNotes(any(PatientNotes.class))).willReturn(patientNotes);

        mockMvc.perform(post("/central/validate-patient-notes/")
                        .content(asJsonString(patientNotes))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePatientNote() throws Exception {
        PatientNotes patientNotes = new PatientNotes("idTest", 1L, "contentTest");


        given(patientNotesClientProxy.addPatientNotes(any(PatientNotes.class))).willReturn(patientNotes);

        mockMvc.perform(post("/central/validate-patient-notes-update/")
                        .param("id", asJsonString(patientNotes.getId()))
                        .content(asJsonString(patientNotes))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePatientById() throws Exception {
        mockMvc.perform(get("/central/delete-patient-notes-by-id/")
                        .param("id", asJsonString("idTest")))
                .andExpect(status().isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
} */
