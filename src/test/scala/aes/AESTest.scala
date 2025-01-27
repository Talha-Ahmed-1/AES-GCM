package aes

import chisel3._
import chisel3.util._
import org.scalatest.flatspec.AnyFlatSpec
import svsim._
import chisel3.simulator._
import java.nio.file.Files
import java.nio.file.Paths
import java.io.File
import scala.reflect.io.Directory

object Simulator extends PeekPokeAPI {
  def simulate[T <: RawModule](
      module: => T
  )(body: (T) => Unit): Unit = {
    makeSimulator
      .simulate(module)({ module =>
        module.controller.setTraceEnabled(true)
        body(module.wrapped)
      })
      .result
  }
  import Simulator._

  private class DefaultSimulator(val workspacePath: String) extends SingleBackendSimulator[verilator.Backend] {
    val backend = verilator.Backend.initializeFromProcessEnvironment()
    val tag = "default"
    val commonCompilationSettings = CommonCompilationSettings()
    val backendSpecificCompilationSettings = verilator.Backend.CompilationSettings(
      traceStyle = Some(verilator.Backend.CompilationSettings.TraceStyle.Vcd(traceUnderscore = true))
    )
  }
  private def makeSimulator: DefaultSimulator = {
    val id = ProcessHandle.current().pid().toString()
    val className = getClass().getName().stripSuffix("$")
    new DefaultSimulator(Files.createDirectories(Paths.get(s"test_run_dir/${className}_${id}")).toString)
  }
}

import Simulator._

class AESTest extends AnyFlatSpec {
  behavior of "AES"

  // it should "test SBox" in {
  //   simulate(new SBox) { dut =>
  //     // dut.io.byteIn.poke(1.U)
  //     dut.clock.step(10)
  //   }
  // }

    // it should "test SubBytes" in {
    //   simulate(new SubBytes) { dut =>
    //     dut.io.stateIn(0)(1).poke(1.U)
    //     // dut.io.stateIn(1).poke(2.U)
    //     // dut.io.stateIn(2).poke(3.U)
    //     // dut.io.stateIn(3).poke(4.U)
    //     // dut.io.stateIn(4).poke(5.U)
    //     // dut.io.stateIn(5).poke(6.U)
    //     // dut.io.stateIn(6).poke(7.U)
    //     // dut.io.stateIn(7).poke(8.U)
    //     // dut.io.stateIn(8).poke(9.U)
    //     // dut.io.stateIn(9).poke(10.U)
    //     // dut.io.stateIn(10).poke(11.U)
    //     // dut.io.stateIn(11).poke(12.U)
    //     // dut.io.stateIn(12).poke(13.U)
    //     // dut.io.stateIn(13).poke(14.U)
    //     // dut.io.stateIn(14).poke(15.U)
    //     // dut.io.stateIn(15).poke(16.U)
    //     dut.io.stateOut(0)(1).expect(0x63.U)
    //     dut.clock.step(10)
    //   }
    // }

    // it should "test SubBytes" in {
    //   simulate(new SubBytes) { dut =>
    //   dut.clock.step(10)
    //       }
    // }

    // it should "test Mix Columns" in {
    // simulate(new MixColumns) { dut =>
    //     // dut.io.byteIn.poke(1.U)
    //     dut.clock.step(10)
    //   }
    // }

    // it should "test Key Expansion" in {
    // simulate(new KeyExpansion) { dut =>
    //     // dut.io.byteIn.poke(1.U)
    //     dut.clock.step(10)
    //   }
    // }

    // it should "test Key Expansion" in {
    //   simulate(new KeyExpansion) { dut =>
    //   dut.clock.step(10)
    //       }
    // }

    it should "test AES" in {
      simulate(new AES) { dut =>
      dut.clock.step(10)
      }
    }

    // it should "test Mix Columns" in {
    //   simulate(new MixColumns) { dut =>
    //     dut.io.stateIn(0)(0).poke(89.U)
    //     dut.io.stateIn(0)(1).poke(69.U)
    //     dut.io.stateIn(0)(2).poke(76.U)
    //     dut.io.stateIn(0)(3).poke(76.U)
    //     dut.io.stateIn(1)(0).poke(79.U)
    //     dut.io.stateIn(1)(1).poke(87.U)
    //     dut.io.stateIn(1)(2).poke(32.U)
    //     dut.io.stateIn(1)(3).poke(83.U)
    //     dut.io.stateIn(2)(0).poke(85.U)
    //     dut.io.stateIn(2)(1).poke(66.U)
    //     dut.io.stateIn(2)(2).poke(77.U)
    //     dut.io.stateIn(2)(3).poke(65.U)
    //     dut.io.stateIn(3)(0).poke(82.U)
    //     dut.io.stateIn(3)(1).poke(73.U)
    //     dut.io.stateIn(3)(2).poke(78.U)
    //     dut.io.stateIn(3)(3).poke(69.U)
    //   dut.clock.step(10)
    //   }
    // }

    // it should "test Key Expansion" in {
    //   simulate(new KeyExpansion) { dut =>
    //     dut.io.keyIn(0)(0).poke(89.U)
    //     dut.io.keyIn(0)(1).poke(69.U)
    //     dut.io.keyIn(0)(2).poke(76.U)
    //     dut.io.keyIn(0)(3).poke(76.U)
    //     dut.io.keyIn(1)(0).poke(79.U)
    //     dut.io.keyIn(1)(1).poke(87.U)
    //     dut.io.keyIn(1)(2).poke(32.U)
    //     dut.io.keyIn(1)(3).poke(83.U)
    //     dut.io.keyIn(2)(0).poke(85.U)
    //     dut.io.keyIn(2)(1).poke(66.U)
    //     dut.io.keyIn(2)(2).poke(77.U)
    //     dut.io.keyIn(2)(3).poke(65.U)
    //     dut.io.keyIn(3)(0).poke(82.U)
    //     dut.io.keyIn(3)(1).poke(73.U)
    //     dut.io.keyIn(3)(2).poke(78.U)
    //     dut.io.keyIn(3)(3).poke(69.U)
    //   dut.clock.step(10)
    //   }
    // }
}