/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.mahout.classifier.sequencelearning.hmm

import org.apache.mahout.math._

import org.apache.mahout.math.{drm, scalabindings}

import scalabindings._
import scalabindings.RLikeOps._
import drm._
import scala.language.asInstanceOf
import scala.collection._
import JavaConversions._
import org.apache.mahout.common.RandomUtils

/**
 *
 * @param numHiddenStates number of hidden states
 * @param numOutputStates number of output states
 */
class HMMModel(val numberOfHiddenStates: Int,
               val numberOfOutputSymbols: Int,
	       val transitionMatrix: Matrix = null,
	       val emissionMatrix: Matrix = null,
  	       val initialProbabilities: Vector = null)  extends java.io.Serializable {

  validate()

  def getNumberOfHiddenStates: Int = {
    numberOfHiddenStates
  }

  def getNumberOfObservableSymbols: Int = {
    numberOfOutputSymbols
  }

  def getInitialProbabilities: Vector = {
    initialProbabilities
  }

  def getEmissionMatrix: Matrix = {
    emissionMatrix
  }

  def getTransitionMatrix: Matrix = {
    transitionMatrix
  }

  def initModelWithRandomParameters(inSeed:Long): Unit = {
    var seed:Long = inSeed
    if (seed == 0) {
      seed = RandomUtils.getRandom().nextInt()
    }

    // initialize the initial Probabilities
    var sum:Double = 0; // used for normalization
    for (i <- 0 to numberOfHiddenStates - 1) {
      val nextRand:Double = RandomUtils.getRandom.nextDouble();
      initialProbabilities.setQuick(i, nextRand);
      sum += nextRand;
    }
    // "normalize" the vector to generate probabilities
    for (i <- 0 to numberOfHiddenStates - 1) {
      initialProbabilities.setQuick(i, initialProbabilities.getQuick(i)/sum);
    }

    // initialize the transition matrix
    for (i <- 0 to numberOfHiddenStates -1) {
      sum = 0
      for (j <- 0 to numberOfHiddenStates - 1) {
        transitionMatrix.setQuick(i, j, RandomUtils.getRandom.nextDouble())
        sum += transitionMatrix.getQuick(i, j)
      }
      // normalize the random values to obtain probabilities
      for (j <- 0 to numberOfHiddenStates - 1) {
        transitionMatrix.setQuick(i, j, transitionMatrix.getQuick(i, j)/sum)
      }
    }

    // initialize the output matrix
    for (i <- 0 to numberOfHiddenStates - 1) {
      sum = 0
      for (j <- 0 to numberOfOutputSymbols - 1) {
        emissionMatrix.setQuick(i, j, RandomUtils.getRandom.nextDouble())
        sum += emissionMatrix.getQuick(i, j)
      }
      // normalize the random values to obtain probabilities
      for (j <- 0 to numberOfOutputSymbols - 1) {
        emissionMatrix.setQuick(i, j, emissionMatrix.getQuick(i, j)/sum)
      }
    }
  }

  /**
   * Write a trained model to the filesystem as a series of DRMs
   * @param pathToModel Directory to which the model will be written
   */
  def dfsWrite(pathToModel: String)(implicit ctx: DistributedContext): Unit = {

    
  }

  /** Model Validation */
  def validate() {
    
  }
}

object HMMModel extends java.io.Serializable {

  val modelBaseDirectory = "/hiddenMarkovModel"

  
  def dfsRead(pathToModel: String)(implicit ctx: DistributedContext): HMMModel = {
    

    
    val model: HMMModel = new HMMModel(0,0)

    model
  }
}