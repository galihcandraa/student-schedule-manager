package com.github.galihcandraa.personal_planner.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.*;
import java.util.Optional;

import com.github.galihcandraa.personal_planner.model.Category;
import com.github.galihcandraa.personal_planner.model.Day;
import com.github.galihcandraa.personal_planner.model.Frequency;
import com.github.galihcandraa.personal_planner.model.Jadwal;
import com.github.galihcandraa.personal_planner.repository.JadwalRepository;
import com.github.galihcandraa.personal_planner.util.DateTimeFormatters;

import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class JadwalServiceTest {
    
    @Mock
    JadwalRepository repository;

    @InjectMocks
    JadwalService service;

    @Test
    public void testAddJadwal_Success_StandardSchedule() {
        Jadwal inputJadwal = new Jadwal(Category.KULIAH, "Dummy Jadwal", "LKJ2", Day.RABU, LocalTime.parse("10:00", DateTimeFormatters.TIME_FORMATTER), LocalTime.parse("11:00", DateTimeFormatters.TIME_FORMATTER), Frequency.RENTANG, LocalDate.parse("20-02-2026", DateTimeFormatters.DATE_FORMATTER), LocalDate.parse("11-06-2026", DateTimeFormatters.DATE_FORMATTER), null);
        
        service.addJadwal(inputJadwal);
        
        // memastikan objek telah disimpan
        verify(repository, times(1)).save(inputJadwal);
    }
    
    @Test
    public void testAddJadwal_Success_WhenFrequencyNotRentang_ShouldSetTanggalSelesaiToNull() {
        Jadwal inputJadwal = new Jadwal(Category.KULIAH, "Dummy Jadwal", "LKJ2", Day.RABU, LocalTime.parse("10:00", DateTimeFormatters.TIME_FORMATTER), LocalTime.parse("11:00", DateTimeFormatters.TIME_FORMATTER), Frequency.SELALU, LocalDate.parse("20-02-2026", DateTimeFormatters.DATE_FORMATTER), null, null);
        
        service.addJadwal(inputJadwal);
        
        // memastikan tanggal selesai bernilai null dan telah disimpan ke repository
        assertNull(inputJadwal.getTanggalSelesai());
        verify(repository, times(1)).save(inputJadwal);
    }
    
    @Test
    public void testAddJadwal_Fail_WhenJamMulaiAfterJamSelesaiAtSameDay() {
        Jadwal inputJadwal = new Jadwal(Category.KULIAH, "Dummy Jadwal", "LKJ2", Day.RABU, LocalTime.parse("15:00", DateTimeFormatters.TIME_FORMATTER), LocalTime.parse("11:00", DateTimeFormatters.TIME_FORMATTER), Frequency.RENTANG, LocalDate.parse("20-02-2026", DateTimeFormatters.DATE_FORMATTER), LocalDate.parse("10-10-2026", DateTimeFormatters.DATE_FORMATTER), null);
        
        assertThrows(ValidationException.class, () -> {
            service.addJadwal(inputJadwal);
        });
        
        // memastikan tidak pernah menyimpan objek yang tidak sesuai
        verify(repository, never()).save(any());
    }
    
    @Test
    public void testAddJadwal_Fail_WhenTanggalMulaiAfterTanggalSelesai() {
        Jadwal inputJadwal = new Jadwal(Category.KULIAH, "Dummy Jadwal", "LKJ2", Day.RABU, LocalTime.parse("10:00", DateTimeFormatters.TIME_FORMATTER), LocalTime.parse("11:00", DateTimeFormatters.TIME_FORMATTER), Frequency.RENTANG, LocalDate.parse("20-02-2026", DateTimeFormatters.DATE_FORMATTER), LocalDate.parse("10-01-2026", DateTimeFormatters.DATE_FORMATTER), null);
        
        assertThrows(ValidationException.class, () -> {
            service.addJadwal(inputJadwal);
        });
        
        // memastikan tidak pernah menyimpan objek yang tidak sesuai
        verify(repository, never()).save(any());
    }

    @Test 
    public void testEditJadwal_Success() {
        Jadwal existJadwal = new Jadwal(Category.KULIAH, "Dummy Jadwal", "LKJ2", Day.RABU, LocalTime.parse("10:00", DateTimeFormatters.TIME_FORMATTER), LocalTime.parse("11:00", DateTimeFormatters.TIME_FORMATTER), Frequency.RENTANG, LocalDate.parse("20-02-2026", DateTimeFormatters.DATE_FORMATTER), LocalDate.parse("11-06-2026", DateTimeFormatters.DATE_FORMATTER), null);

        Jadwal editJadwal = new Jadwal(Category.KULIAH, "Edit Jadwal", "LKJ2", Day.RABU, LocalTime.parse("10:00", DateTimeFormatters.TIME_FORMATTER), LocalTime.parse("11:00", DateTimeFormatters.TIME_FORMATTER), Frequency.RENTANG, LocalDate.parse("20-02-2026", DateTimeFormatters.DATE_FORMATTER), LocalDate.parse("11-06-2026", DateTimeFormatters.DATE_FORMATTER), null);

        when(repository.findById(1L)).thenReturn(Optional.of(existJadwal));

        service.editJadwal(editJadwal, 1);

        // memastikan objek tangkapan captor sesuai dengan jadwal yang telah di edit
        ArgumentCaptor<Jadwal> jadwalCaptor = ArgumentCaptor.forClass(Jadwal.class);
        verify(repository, times(1)).save(jadwalCaptor.capture());
        Jadwal savedResult = jadwalCaptor.getValue();
        assertEquals("Edit Jadwal", savedResult.getJudul());
    }

    @Test
    public void testEditJadwal_Fail_WhenIdNotFound() {
        when(repository.findById(10L)).thenReturn(Optional.empty());
        
        Jadwal updateJadwal = new Jadwal();

        // memastikan mereturn exception jika id tidak ditemukan
        assertThrows(ResponseStatusException.class, () -> {
            service.editJadwal(updateJadwal, 10);
        });
        verify(repository, never()).save(any());
    }

    @Test
    public void testDeleteJadwal_Success() {
        when(repository.existsById(5L)).thenReturn(true);
        
        service.deleteById(5);
        
        // memastikan keberadaan dan menjalankan method delete
        verify(repository, times(1)).existsById(5L);
        verify(repository, times(1)).deleteById(5L);
    }
    
    @Test
    public void testDeleteJadwal_Fail_WhenIdNotFound() {
        when(repository.existsById(8L)).thenReturn(false);
        
        // memastikan mereturn exception jika id tidak ditemukan
        assertThrows(ValidationException.class, () -> {
            service.deleteById(8);
        });
        verify(repository, never()).deleteById(any());
    }
}
