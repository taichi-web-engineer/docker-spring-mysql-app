package com.testproject.backend.repository

import com.testproject.backend.entity.TestTable
import org.springframework.data.repository.CrudRepository

interface TestRepository : CrudRepository<TestTable, Int>
