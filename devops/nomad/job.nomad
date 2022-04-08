job "price-action" {
  datacenters = ["dc1"]
  type = "service"
  group "price-action" {
    count = 1
    task "price-action" {
      driver = "java"
      config {
        jar_path = "/Users/alex.gaas/Desktop/projects/price-action/build/libs/price-action-1.0.0.jar"
        jvm_options = ["-Xmx256m", "-Xms128m"]
      }
      resources {
        cpu = 500
        memory = 500
        network {
          port "http" {}
        }
      }
      service {
        name = "price-action"
        port = "http"
      }
    }
    restart {
      attempts = 1
    }
  }
}
