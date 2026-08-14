package com.github.galihcandraa.personal_planner.controller;

import java.util.List;

import com.github.galihcandraa.personal_planner.service.JadwalService;

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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/jadwal")
public class JadwalController {

    private final JadwalService service;

    public JadwalController(JadwalService service) {
        this.service = service;
    }

    // mengambil semua data jadwal
    @GetMapping
    public ResponseEntity<List<Jadwal>> getAll() {
        return ResponseEntity.ok(service.showJadwal());
    }

    // menambahkan data jadwal
    @PostMapping
    public ResponseEntity<?> add(@RequestBody JadwalRequest request) {
        Jadwal jadwalBaru = request.toEntity();

        service.addJadwal(jadwalBaru);
        return ResponseEntity.ok("Berhasil ditambahkan");
    }
    
    // mencari id yang cocok
    @GetMapping("/{id}")
    public ResponseEntity<Jadwal> getById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    // mengedit data jadwal
    @PutMapping("/{id}")
    public ResponseEntity<String> edit(@PathVariable long id, @RequestBody JadwalRequest request) {
        Jadwal jadwalEdit = request.toEntity();

        service.editJadwal(jadwalEdit, id);
        return ResponseEntity.ok("Berhasil diedit");
    }

    // menghapus data jadwal sesuai id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Berhasil menghapus jadwal dengan id " + id);
    }

    // melakukan proses searching sesuai tipe
    @GetMapping("/search")
    public ResponseEntity<List<Jadwal>> search(@RequestParam SearchType type, String value) {
        return ResponseEntity.ok(service.searchByCondition(type, value));
    }

    // melakukan proses sorting sesuai tipe
    @GetMapping("/sort")
    public ResponseEntity<List<Jadwal>> sort(@RequestParam SortType type, SortOrder order) {
        return ResponseEntity.ok(service.sortByCondition(type, order));
    }

    // mereset data jadwal yang tersimpan
    @DeleteMapping("/reset")
    public ResponseEntity<String> deleteAll() {
        service.reset();
        return ResponseEntity.ok("Berhasil menghapus semua jadwal");
    }
}
