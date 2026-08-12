package com.github.galihcandraa.personal_planner.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.github.galihcandraa.personal_planner.service.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.galihcandraa.personal_planner.dto.JadwalRequest;
import com.github.galihcandraa.personal_planner.model.*;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/jadwal")
public class JadwalController {

    private final JadwalService service;

    public JadwalController(JadwalService service) {
        this.service = service;
    }

    @GetMapping
    public List<Jadwal> getAll() {
        return service.showJadwal();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody JadwalRequest request) {
        Category kategori = service.parseCategory(request.getKategori());
        Day hari = service.parseDay(request.getHari());
        LocalTime jamMulai = service.parseTime(request.getJamMulai());
        LocalTime jamSelesai = service.parseTime(request.getJamSelesai());
        service.validateTimeLogic(jamMulai, jamSelesai);
        service.validateTimeConflict(hari, jamMulai, jamSelesai, null);
        
        Frequency frekuensi = service.parseFrequency(request.getFrekuensi());
        LocalDate tglMulai = service.parseDate(request.getTglMulai());
        LocalDate tglSelesai = service.parseDate(request.getTglSelesai());
        service.validateDateLogic(tglMulai, tglSelesai);

        service.addData(kategori, request.getJudul(), request.getLokasi(), hari, jamMulai, jamSelesai, frekuensi, tglMulai, tglSelesai, request.getDeskripsi());
        return ResponseEntity.ok("Berhasil ditambahkan");
    }
    
    @GetMapping("/{id}")
    public Jadwal getById(@PathVariable String id) {
        return service.findById(id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> edit(@PathVariable String id, @RequestBody JadwalRequest request) {
        Category kategori = service.parseCategory(request.getKategori());
        Day hari = service.parseDay(request.getHari());
        LocalTime jamMulai = service.parseTime(request.getJamMulai());
        LocalTime jamSelesai = service.parseTime(request.getJamSelesai());
        service.validateTimeLogic(jamMulai, jamSelesai);
        
        Frequency frekuensi = service.parseFrequency(request.getFrekuensi());
        LocalDate tglMulai = service.parseDate(request.getTglMulai());
        LocalDate tglSelesai = service.parseDate(request.getTglSelesai());
        service.validateDateLogic(tglMulai, tglSelesai);
    
        service.editData(id, kategori, request.getJudul(), request.getLokasi(), hari, jamMulai, jamSelesai, frekuensi, tglMulai, tglSelesai, request.getDeskripsi());
        return ResponseEntity.ok("Berhasil diedit");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok("Berhasil dihapus");
    }
}
