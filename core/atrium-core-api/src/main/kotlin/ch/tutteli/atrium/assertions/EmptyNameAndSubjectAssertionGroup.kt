package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.reporting.RawString
import ch.tutteli.atrium.reporting.translating.Translatable
import ch.tutteli.atrium.reporting.translating.Untranslatable

/**
 * Represents a base class for [IAssertionGroup]s which have an empty [name] and [subject].
 *
 * @constructor Represents a base class for [IAssertionGroup]s which have an empty [name] and [subject].
 * @param type The type of the group, e.g. [IInvisibleAssertionGroupType].
 * @param assertions The assertions of this group.
 */
abstract class EmptyNameAndSubjectAssertionGroup(override val type: IAssertionGroupType, override val assertions: List<IAssertion>) : IAssertionGroup {

    /**
     * [Untranslatable.EMPTY] -- an empty string as [Untranslatable].
     */
    override val name: Translatable = Untranslatable.EMPTY

    /**
     * The empty string.
     */
    override val subject: Any = RawString.EMPTY

    /**
     * @suppress
     */
    override fun toString(): String {
        return javaClass.simpleName + " " + assertions
    }
}
