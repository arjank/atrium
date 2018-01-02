package ch.tutteli.atrium.assertions.iterable.contains.creators

import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.assertions.DefaultListAssertionGroupType
import ch.tutteli.atrium.assertions.DescriptionIterableAssertion
import ch.tutteli.atrium.assertions.FixHoldsAssertionGroup
import ch.tutteli.atrium.creating.AssertionCollector
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.reporting.RawString


internal fun <E : Any> createExplanatoryAssertions(assertionCreator: (AssertionPlant<E>.() -> Unit)?, list: List<E?>)
    = collectIterableAssertionsForExplanation(assertionCreator, list.firstOrNull())

internal fun <E : Any> collectIterableAssertionsForExplanationWithFirst(assertionCreator: (AssertionPlant<E>.() -> Unit)?, first: E?): List<Assertion> {
    return if (first != null) {
        collectIterableAssertionsForExplanation(assertionCreator, first)
    } else {
        collectIterableAssertionsForExplanation("subject was null, cannot down-cast it to the non-nullable type", assertionCreator, null)
    }
}

internal fun <E : Any> collectIterableAssertionsForExplanation(assertionCreator: (AssertionPlant<E>.() -> Unit)?, subject: E?)
    = collectIterableAssertionsForExplanation("The iterator was empty and thus no subject available", assertionCreator, subject)

internal fun <E : Any> collectIterableAssertionsForExplanation(reason: String, assertionCreator: (AssertionPlant<E>.() -> Unit)?, subject: E?)
    = AssertionCollector
    .throwIfNoAssertionIsCollected
    .collectAssertionsForExplanation(reason, DescriptionIterableAssertion.WARNING_SUBJECT_NOT_SET, assertionCreator, subject)

internal fun createEntryAssertion(explanatoryAssertions: List<Assertion>, found: Boolean) =
    FixHoldsAssertionGroup(DefaultListAssertionGroupType, DescriptionIterableAssertion.AN_ENTRY_WHICH, RawString.EMPTY, explanatoryAssertions, found)

internal fun <E : Any> allCreatedAssertionsHold(subject: E?, assertionCreator: (AssertionPlant<E>.() -> Unit)?): Boolean = when (subject) {
    null -> assertionCreator == null
    else -> assertionCreator != null &&
        AtriumFactory.newCheckingPlant(subject)
            .addAssertionsCreatedBy(assertionCreator)
            .allAssertionsHold()
}
