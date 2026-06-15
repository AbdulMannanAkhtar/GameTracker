package com.example.gametracker

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://ifebubvmjaumpxudaxkm.supabase.co",
        supabaseKey = "sb_publishable_DFsh0tN1-YRKk13uPaQDAg_5bNKUzCa"
    ){
        install(Auth)
        install(Postgrest)
    }
}